package com.HoopStretchApi.service.implementations;

import com.HoopStretchApi.exception.NotFoundException;
import com.HoopStretchApi.mapper.ExerciseMapper;
import com.HoopStretchApi.model.dto.exercise.ExerciseFilterDto;
import com.HoopStretchApi.model.dto.exercise.ExerciseRequestDto;
import com.HoopStretchApi.model.dto.exercise.ExerciseResponseDto;
import com.HoopStretchApi.model.dto.pagination.PaginationRequestDto;
import com.HoopStretchApi.model.dto.pagination.PaginationResponseDto;
import com.HoopStretchApi.model.entity.EquipmentItem;
import com.HoopStretchApi.model.entity.Exercise;
import com.HoopStretchApi.model.entity.MuscleGroup;
import com.HoopStretchApi.repository.EquipmentItemRepository;
import com.HoopStretchApi.repository.ExerciseRepository;
import com.HoopStretchApi.repository.MuscleGroupRepository;
import com.HoopStretchApi.service.ExerciseService;
import com.HoopStretchApi.specification.ExerciseSpecifications;
import com.HoopStretchApi.util.PaginationUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final MuscleGroupRepository muscleGroupRepository;
    private final EquipmentItemRepository equipmentItemRepository;
    private final ExerciseSpecifications exerciseSpecifications;
    private final ExerciseMapper exerciseMapper;

    @Override
    public PaginationResponseDto<ExerciseResponseDto> getExercises(final PaginationRequestDto paginationRequestDto, final ExerciseFilterDto exerciseFilterDto) {
        final Pageable pageable = PaginationUtils.getPageable(paginationRequestDto);
        final Specification<Exercise> spec = exerciseSpecifications.buildFilters(exerciseFilterDto);
        final Page<Exercise> exercisesPage = exerciseRepository.findAll(spec, pageable);
        final List<ExerciseResponseDto> exercises = exercisesPage.getContent().stream()
                .map(exerciseMapper::toExerciseResponseDto)
                .toList();

        return new PaginationResponseDto<>(
                exercises,
                exercisesPage.getNumber(),
                exercisesPage.getSize(),
                exercisesPage.getTotalElements(),
                exercisesPage.getTotalPages()
        );
    }

    @Override
    public ExerciseResponseDto getExerciseById(final Long id) {
        return exerciseRepository.findById(id)
                .map(exerciseMapper::toExerciseResponseDto)
                .orElseThrow(()-> new NotFoundException("Exercise not found"));

    }

    @Transactional
    @Override
    public ExerciseResponseDto createExercise(final ExerciseRequestDto exerciseRequestDto) {
        final Exercise exercise = exerciseMapper.toExercise(exerciseRequestDto);
        final Set<MuscleGroup> muscleGroups = new HashSet<>(muscleGroupRepository.findAllById(exerciseRequestDto.getMuscleGroupIds()));
        final Set<EquipmentItem> equipment = new HashSet<>(equipmentItemRepository.findAllById(exerciseRequestDto.getEquipmentIds()));

        if (muscleGroups.size() != exerciseRequestDto.getMuscleGroupIds().size()) {
            throw new NotFoundException("Some muscle groups not found");
        }

        exercise.setMuscleGroups(muscleGroups);
        exercise.setEquipment(equipment);
        final Exercise savedExercise = exerciseRepository.save(exercise);
        return exerciseMapper.toExerciseResponseDto(savedExercise);
    }

    @Transactional
    @Override
    public ExerciseResponseDto updateExercise(final Long id, final ExerciseRequestDto exerciseRequestDto) {
        final Exercise exercise = exerciseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Exercise not found"));

        exerciseMapper.updateExerciseFromDto(exerciseRequestDto, exercise);
        final Exercise savedExercise = exerciseRepository.save(exercise);

        return exerciseMapper.toExerciseResponseDto(savedExercise);
    }

    @Override
    public void deleteExercise(final Long id){
        final Exercise exercise = exerciseRepository.findById(id)
                        .orElseThrow(()-> new NotFoundException("Exercise not found"));
        exerciseRepository.delete(exercise);
    }
}
