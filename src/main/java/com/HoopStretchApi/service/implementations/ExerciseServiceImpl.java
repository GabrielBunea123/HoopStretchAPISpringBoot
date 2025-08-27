package com.HoopStretchApi.service.implementations;

import com.HoopStretchApi.mapper.ExerciseMapper;
import com.HoopStretchApi.model.dto.exercise.ExerciseFilterDto;
import com.HoopStretchApi.model.dto.exercise.ExerciseResponseDto;
import com.HoopStretchApi.model.dto.pagination.PaginationRequestDto;
import com.HoopStretchApi.model.dto.pagination.PaginationResponseDto;
import com.HoopStretchApi.model.entity.Exercise;
import com.HoopStretchApi.repository.ExerciseRepository;
import com.HoopStretchApi.service.ExerciseService;
import com.HoopStretchApi.specification.ExerciseSpecifications;
import com.HoopStretchApi.util.PaginationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

@RequiredArgsConstructor
public class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseRepository exerciseRepository;
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
}
