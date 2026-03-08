package com.HoopStretchApi.service.implementations;

import com.HoopStretchApi.exception.NotFoundException;
import com.HoopStretchApi.mapper.ProtocolExerciseMapper;
import com.HoopStretchApi.mapper.ProtocolMapper;
import com.HoopStretchApi.model.dto.pagination.PaginationRequestDto;
import com.HoopStretchApi.model.dto.pagination.PaginationResponseDto;
import com.HoopStretchApi.model.dto.protocol.ProtocolExerciseRequestDto;
import com.HoopStretchApi.model.dto.protocol.ProtocolFilterDto;
import com.HoopStretchApi.model.dto.protocol.ProtocolRequestDto;
import com.HoopStretchApi.model.dto.protocol.ProtocolResponseDto;
import com.HoopStretchApi.model.entity.Exercise;
import com.HoopStretchApi.model.entity.Protocol;
import com.HoopStretchApi.model.entity.ProtocolExercise;
import com.HoopStretchApi.model.entity.User;
import com.HoopStretchApi.repository.ExerciseRepository;
import com.HoopStretchApi.repository.ProtocolExerciseRepository;
import com.HoopStretchApi.repository.ProtocolRepository;
import com.HoopStretchApi.service.ProtocolService;
import com.HoopStretchApi.service.UserService;
import com.HoopStretchApi.specification.ProtocolSpecifications;
import com.HoopStretchApi.util.PaginationUtils;
import com.HoopStretchApi.util.enums.ProtocolPurpose;
import com.HoopStretchApi.util.enums.ProtocolVisibility;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProtocolServiceImpl implements ProtocolService {

    private final ProtocolRepository protocolRepository;
    private final ProtocolExerciseRepository protocolExerciseRepository;
    private final ExerciseRepository exerciseRepository;
    private final ProtocolMapper protocolMapper;
    private final ProtocolExerciseMapper protocolExerciseMapper;
    private final ProtocolSpecifications protocolSpecifications;
    private final UserService userService;

    // TODO: This should be accessed only by certain roles
    @Override
    @Transactional
    public ProtocolResponseDto createPublicProtocol(final ProtocolRequestDto protocolRequestDto) {
        return createProtocol(protocolRequestDto, ProtocolVisibility.PUBLIC, false, null);
    }

    @Override
    public ProtocolResponseDto createUserProtocol(final ProtocolRequestDto protocolRequestDto, final String username) {
        final User owner = userService.getUserByUsername(username);
        return createProtocol(protocolRequestDto, ProtocolVisibility.PRIVATE, false, owner);
    }


    @Override
    public ProtocolResponseDto createMobilityTestProtocol(final List<Long> exerciseIds) {

        final List<Exercise> exercises = getAndValidateExercises(exerciseIds);

        final Protocol protocol = Protocol.builder()
                .purpose(ProtocolPurpose.ASSESSMENT)
                .generated(false)
                .visibility(ProtocolVisibility.PUBLIC)
                .build();

        final Set<ProtocolExercise> protocolExercises =
                createProtocolExercises(protocol, exercises);

        protocolRepository.save(protocol);
        protocolExerciseRepository.saveAll(protocolExercises);

        return protocolMapper.toProtocolResponseDto(protocol);
    }

    @Override
    public ProtocolResponseDto getUserProtocolById(final String username, final Long id) {
        final User owner = userService.getUserByUsername(username);
        final Optional<Protocol> protocol = protocolRepository.findByIdAndOwnerId(id, owner.getId());
        if (protocol.isEmpty()){
            throw new NotFoundException("Protocol not found or not owned by user");
        }
        return protocolMapper.toProtocolResponseDto(protocol.get());
    }

    @Override
    public PaginationResponseDto<ProtocolResponseDto> getUserProtocols(
            final String username,
            final PaginationRequestDto paginationRequestDto,
            final ProtocolFilterDto protocolFilterDto) {

        final Pageable pageable = PaginationUtils.getPageable(paginationRequestDto);
        final Specification<Protocol> spec = protocolSpecifications.buildFilters(username, protocolFilterDto);
        final Page<Protocol> protocolsPage = protocolRepository.findAll(spec, pageable);
        final List<ProtocolResponseDto> protocols = protocolsPage.getContent().stream()
                .map(protocolMapper::toProtocolResponseDto)
                .toList();

        return new PaginationResponseDto<>(
                protocols,
                protocolsPage.getNumber(),
                protocolsPage.getSize(),
                protocolsPage.getTotalElements(),
                protocolsPage.getTotalPages()
        );
    }

    private List<Exercise> getAndValidateExercises(final List<Long> exerciseIds) {

        final List<Exercise> exercises = exerciseRepository.findAllById(exerciseIds);

        if (exercises.size() != exerciseIds.size()) {
            final Set<Long> foundIds = exercises.stream()
                    .map(Exercise::getId)
                    .collect(Collectors.toSet());

            final Set<Long> missingIds = exerciseIds.stream()
                    .filter(id -> !foundIds.contains(id))
                    .collect(Collectors.toSet());

            throw new NotFoundException(
                    "Exercises with the following ids not found: " + missingIds
            );
        }

        return exercises;
    }

    private Set<ProtocolExercise> createProtocolExercises(
            final Protocol protocol,
            final List<Exercise> exercises
    ) {
        final Set<ProtocolExercise> protocolExercises = new LinkedHashSet<>();

        int orderIndex = 0;
        for (final Exercise exercise : exercises) {
            protocolExercises.add(
                    ProtocolExercise.builder()
                            .protocol(protocol)
                            .exercise(exercise)
                            .orderIndex(orderIndex++)
                            .build()
            );
        }

        protocol.setExercises(protocolExercises);
        return protocolExercises;
    }

    private ProtocolResponseDto createProtocol(
            final ProtocolRequestDto protocolRequestDto,
            final ProtocolVisibility visibility,
            final boolean generated,
            final User owner
    ) {
        Protocol protocol = protocolMapper.toProtocol(protocolRequestDto, visibility, generated, owner);
        protocol = protocolRepository.save(protocol);

        final Set<ProtocolExercise> exercises = buildProtocolExercises(protocolRequestDto.getExercises(), protocol);
        final List<ProtocolExercise> savedExercises = protocolExerciseRepository.saveAll(exercises);
        protocol.setExercises(new LinkedHashSet<>(savedExercises));

        return protocolMapper.toProtocolResponseDto(protocol);
    }

    private Set<ProtocolExercise> buildProtocolExercises(
            final List<ProtocolExerciseRequestDto> protocolExercisesRequestDto,
            final Protocol protocol) {

        final List<Long> exerciseIds = new ArrayList<>();
        for (final ProtocolExerciseRequestDto dto : protocolExercisesRequestDto) {
            exerciseIds.add(dto.getExerciseId());
        }

        final Map<Long, Exercise> exerciseMap = new HashMap<>();
        final List<Exercise> exercises = exerciseRepository.findAllById(exerciseIds);
        for (final Exercise exercise : exercises) {
            exerciseMap.put(exercise.getId(), exercise);
        }

        final Set<ProtocolExercise> protocolExercises = new LinkedHashSet<>();
        for (int orderIndex = 0; orderIndex < protocolExercisesRequestDto.size(); orderIndex++) {
            final ProtocolExerciseRequestDto dto = protocolExercisesRequestDto.get(orderIndex);
            final Exercise exercise = exerciseMap.get(dto.getExerciseId());

            if (exercise == null) {
                throw new NotFoundException("Exercise not found for ID: " + dto.getExerciseId());
            }

            final ProtocolExercise protocolExercise = protocolExerciseMapper.toProtocolExercise(
                    dto.getDuration(), protocol, exercise, orderIndex
            );
            protocolExercises.add(protocolExercise);
        }

        return protocolExercises;
    }
}
