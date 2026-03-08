package com.HoopStretchApi.service.implementations;

import com.HoopStretchApi.mapper.ProtocolMapper;
import com.HoopStretchApi.model.dto.mobilityAssessment.MobilityAssessmentResponseDto;
import com.HoopStretchApi.model.dto.mobilityAssessment.MobilityScoreDto;
import com.HoopStretchApi.model.dto.protocol.ProtocolGenerationRequestDto;
import com.HoopStretchApi.model.dto.protocol.ProtocolResponseDto;
import com.HoopStretchApi.model.entity.*;
import com.HoopStretchApi.repository.*;
import com.HoopStretchApi.service.MobilityAssessmentService;
import com.HoopStretchApi.service.ProtocolGenerationService;
import com.HoopStretchApi.service.UserService;
import com.HoopStretchApi.util.enums.ExerciseType;
import com.HoopStretchApi.util.enums.MobilityArea;
import com.HoopStretchApi.util.enums.ProtocolPurpose;
import com.HoopStretchApi.util.enums.ProtocolVisibility;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.HoopStretchApi.util.Constants.PROTOCOL_EXERCISE_TYPE_RATIOS_BY_PURPOSE;

@Slf4j
@Service
@RequiredArgsConstructor
// TODO: This part will be moved to python microservice and made differently
public class ProtocolGenerationServiceImpl implements ProtocolGenerationService {

    private static final double MAX_PRIORITY_SCORE = 10.0;
    private static final double DURATION_SOFT_OVERFLOW_TOLERANCE = 1.0;
    private static final double DAMPENING_FACTOR = 0.5;
    private static final int BASE_EXERCISE_DURATION_SECONDS = 60;

    private final ExerciseRepository exerciseRepository;
    private final MobilityAssessmentService mobilityAssessmentService;
    private final UserService userService;
    private final ProtocolMapper protocolMapper;
    private final ProtocolRepository protocolRepository;
    private final ProtocolExerciseRepository protocolExerciseRepository;
    private final MuscleGroupRepository muscleGroupRepository;
    private final MobilityAssessmentRepository mobilityAssessmentRepository;

    // TODO: For now only generate protocols for pregame/daily/postgame (body zones or so on protocols, later)
    @Override
    @Transactional
    public ProtocolResponseDto generateProtocol(
            final String username, 
            final ProtocolGenerationRequestDto requestDto) {
        final User owner = userService.getUserByUsername(username);

        final Map<MobilityArea, Double> mobilityPriorityByArea = resolveMobilityAreaPriorities(owner);
        final List<Exercise> allExercises = exerciseRepository.findAll();
        final List<Exercise> selectedExercises = handleExerciseSelection(
                requestDto,
                allExercises,
                mobilityPriorityByArea);

        final Protocol protocol = buildAndSaveGeneratedProtocol(
                owner,
                selectedExercises,
                requestDto.getDurationSeconds(),
                requestDto.getPurpose());

        return protocolMapper.toProtocolResponseDto(protocol);
    }

    private List<Exercise> handleExerciseSelection(
            final ProtocolGenerationRequestDto requestDto, 
            final List<Exercise> allExercises, 
            final Map<MobilityArea, Double> mobilityPriorityByArea) {
        final List<Exercise> exercisePool = buildExercisePoolForProtocolPurpose(allExercises, requestDto.getPurpose(), requestDto.getDurationSeconds());

        final Map<Long, Double> exerciseScores = scoreAllExercises(exercisePool, mobilityPriorityByArea);
        return selectExercises(
                exercisePool,
                exerciseScores,
                requestDto.getDurationSeconds());
    }

    private Map<MobilityArea, Double> resolveMobilityAreaPriorities(final User user){
        final Optional<Long> latestAssessmentId = mobilityAssessmentRepository.findMaxAssessmentIdByUserId(user.getId());
        return latestAssessmentId
                .map(this :: retrieveUserMobilityAreaPriorities)
                .orElseGet(this :: buildFallbackMobilityAreaPriorities);
    }

    private Map<MobilityArea, Double> buildFallbackMobilityAreaPriorities(){
        final Map<MobilityArea, Double> priorities = new EnumMap<>(MobilityArea.class);
        final List<MobilityArea> rootMobilityAreas = Arrays.stream(MobilityArea.values())
                .filter(area -> area.getParent() == null)
                .toList();
        for (final MobilityArea mobilityArea : rootMobilityAreas) {
            final List<MuscleGroup> muscleGroups = muscleGroupRepository.findAllByMobilityArea(mobilityArea);
            final double averagePriorityScore = muscleGroups.stream()
                    .mapToDouble(MuscleGroup::getDefaultPriorityNumber)
                    .average()
                    .orElse(0.0);
            final double invertedAveragePriorityScore = (MAX_PRIORITY_SCORE + 1.0) - averagePriorityScore;
            priorities.merge(mobilityArea, invertedAveragePriorityScore, Double::sum);
        }
        return priorities;
    }

    private Map<MobilityArea, Double> retrieveUserMobilityAreaPriorities(final Long latestAssessmentId){
        final MobilityAssessmentResponseDto mobilityAssessment = mobilityAssessmentService.getAssessmentMainAreasByAssessmentId(latestAssessmentId);
        final List<MobilityScoreDto> mobilityScores = mobilityAssessment.getMobilityScores();
        final Map<MobilityArea, Double> priorities = new EnumMap<>(MobilityArea.class);
        for (final MobilityScoreDto mobilityScore : mobilityScores) {
            priorities.merge(mobilityScore.getMobilityArea(), (double) mobilityScore.getScore(), Double :: sum);
        }
        return priorities;
    }
    
    private List<Exercise> buildExercisePoolForProtocolPurpose(
            final List<Exercise> exercises,
            final ProtocolPurpose purpose,
            final int durationSeconds) {

        final Map<ExerciseType, Double> ratios = PROTOCOL_EXERCISE_TYPE_RATIOS_BY_PURPOSE.get(purpose);
        if (ratios == null) {
            return new ArrayList<>(exercises);
        }

        // Estimate total exercise count from budget (will be refined during selection,
        // but we need an approximation to compute per-type quotas)
        final int estimatedTotal = durationSeconds / 60; // assumes ~60s per exercise
        final Map<ExerciseType, Integer> exerciseCountsPerType = calculateExerciseCountsPerType(ratios, estimatedTotal);

        final Map<ExerciseType, List<Exercise>> exercisesByType = exercises.stream()
                .collect(Collectors.groupingBy(Exercise::getType));

        final List<Exercise> exercisePool = new ArrayList<>();
        ratios.forEach((exerciseType, ratioValue) -> {
            final List<Exercise> candidates = exercisesByType.getOrDefault(exerciseType, List.of());
            final int exerciseCounts = exerciseCountsPerType.get(exerciseType);
            final int exercisesToTake = Math.min(exerciseCounts, candidates.size());
            exercisePool.addAll(candidates.subList(0, exercisesToTake));
        });

        return exercisePool;
    }

    private Map<ExerciseType, Integer> calculateExerciseCountsPerType(
            final Map<ExerciseType, Double> ratios,
            final int totalExerciseCount) {

        final Map<ExerciseType, Integer> countPerType = new EnumMap<>(ExerciseType.class);
        final AtomicInteger totalAllocated = new AtomicInteger();

        // First pass: round to nearest
        ratios.forEach((exerciseType, ratio) -> {
            final int count = (int) Math.round(ratio * totalExerciseCount);
            countPerType.put(exerciseType, count);
            totalAllocated.addAndGet(count);
        });

        // Second pass: correct rounding drift against the type with the largest ratio
        final int roundingDrift = totalExerciseCount - totalAllocated.get();
        if (roundingDrift != 0) {
            final ExerciseType dominantType = ratios.entrySet().stream()
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey)
                    .orElseThrow();
            countPerType.merge(dominantType, roundingDrift, Integer::sum);
        }

        return countPerType;
    }

    private Map<Long, Double> scoreAllExercises(
            final List<Exercise> exercises,
            final Map<MobilityArea, Double> mobilityPriorityByArea){
        final Map<Long, Double> scores = new HashMap<>(exercises.size());
        for (final Exercise exercise : exercises) {
            scores.put(exercise.getId(), scoreExercise(exercise, mobilityPriorityByArea));
        }
        return scores;
    }

    private double scoreExercise(
            final Exercise exercise,
            final Map<MobilityArea, Double> mobilityPriorityByArea){
        // TODO: here the logic should be re-thought(with a primary muscle group and secondary muscle groups)
        return exercise.getMuscleGroups().stream()
                .map(MuscleGroup::getMobilityArea)
                .mapToDouble(area->mobilityPriorityByArea.getOrDefault(area, 0.0))
                .max()
                .orElse(0.0);
    }

    private List<Exercise> selectExercises(
            final List<Exercise> exercises,
            final Map<Long, Double> exerciseScores,
            final int durationSeconds) {

        Map<Long, Double> finalExerciseScores = new HashMap<>(exerciseScores);
        final List<Exercise> finalExercisePool = new ArrayList<>(exercises);
        final List<Exercise> selectedExercises = new ArrayList<>();

        final int softDurationBudget = (int) (durationSeconds * DURATION_SOFT_OVERFLOW_TOLERANCE);
        int accumulatedDuration = 0;
        while(!finalExercisePool.isEmpty()){
            final Exercise bestExercise = finalExercisePool.stream()
                    .max(Comparator.comparingDouble(exercise -> exerciseScores.getOrDefault(exercise.getId(), 0.0)))
                    .orElseThrow();

            final int exerciseEffectiveDuration = effectiveDuration(bestExercise, BASE_EXERCISE_DURATION_SECONDS);
            if (accumulatedDuration + exerciseEffectiveDuration > softDurationBudget) {
                finalExercisePool.remove(bestExercise);
                continue;
            }

            selectedExercises.add(bestExercise);
            accumulatedDuration += exerciseEffectiveDuration;
            finalExercisePool.remove(bestExercise);
            finalExerciseScores = penalizeOverlappingMobilityAreasInExercises(bestExercise, finalExercisePool, finalExerciseScores);

        }
        return selectedExercises;
    }

    private int effectiveDuration(final Exercise exercise, final int baseDurationSeconds) {
        return exercise.isDoubleSided() ? baseDurationSeconds * 2 : baseDurationSeconds;
    }

    private Map<Long, Double> penalizeOverlappingMobilityAreasInExercises(
            final Exercise justSelectedExercise,
            final List<Exercise> remainingExercisePool,
            final Map<Long, Double> currentExercisesScores) {

        final Set<MobilityArea> coveredAreas = extractMobilityAreas(justSelectedExercise);
        if (coveredAreas.isEmpty()) {
            return currentExercisesScores;
        }

        final Map<Long, Double> updatedExercisesScores = new HashMap<>(currentExercisesScores);
        for (final Exercise exerciseCandidate : remainingExercisePool) {
            final boolean coversAlreadyTargetedArea = extractMobilityAreas(exerciseCandidate).stream()
                    .anyMatch(coveredAreas::contains);
            if (coversAlreadyTargetedArea) {
                updatedExercisesScores.computeIfPresent(exerciseCandidate.getId(),
                        (id, score) -> score * DAMPENING_FACTOR);
            }
        }

        return updatedExercisesScores;
    }

    private Set<MobilityArea> extractMobilityAreas(final Exercise exercise) {
        return exercise.getMuscleGroups().stream()
                .map(MuscleGroup::getMobilityArea)
                .collect(Collectors.toSet());
    }

    private Protocol buildAndSaveGeneratedProtocol(
            final User user,
            final List<Exercise> exercises,
            final int durationSeconds,
            final ProtocolPurpose purpose) {

        final int totalEffectiveDuration = exercises.stream()
                .mapToInt(e -> effectiveDuration(e, BASE_EXERCISE_DURATION_SECONDS))
                .sum();

        final Protocol protocol = Protocol.builder()
                .owner(user)
                .generated(true)
                .purpose(purpose)
                .visibility(ProtocolVisibility.PRIVATE)
                .durationSeconds(totalEffectiveDuration)
                .exercises(new LinkedHashSet<>())
                .build();

        final LinkedHashSet<ProtocolExercise> protocolExercises = new LinkedHashSet<>();
        for (int orderIndex = 0; orderIndex < exercises.size(); orderIndex++) {
            final ProtocolExercise protocolExercise = ProtocolExercise.builder()
                    .protocol(protocol)
                    .exercise(exercises.get(orderIndex))
                    .duration(effectiveDuration(exercises.get(orderIndex), BASE_EXERCISE_DURATION_SECONDS))
                    .orderIndex(orderIndex)
                    .build();
            protocolExercises.add(protocolExercise);
        }
        final Protocol savedProtocol = protocolRepository.save(protocol);
        final List<ProtocolExercise> savedProtocolExercises = protocolExerciseRepository.saveAll(protocolExercises);
        protocol.setExercises(new LinkedHashSet<>(savedProtocolExercises));
        return savedProtocol;
    }

}
