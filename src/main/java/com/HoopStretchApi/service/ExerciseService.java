package com.HoopStretchApi.service;

import com.HoopStretchApi.model.dto.exercise.ExerciseFilterDto;
import com.HoopStretchApi.model.dto.exercise.ExerciseRequestDto;
import com.HoopStretchApi.model.dto.exercise.ExerciseResponseDto;
import com.HoopStretchApi.model.dto.pagination.PaginationRequestDto;
import com.HoopStretchApi.model.dto.pagination.PaginationResponseDto;

public interface ExerciseService {
    PaginationResponseDto<ExerciseResponseDto> getExercises(final PaginationRequestDto paginationRequestDto, final ExerciseFilterDto exerciseFilterDto);
    ExerciseResponseDto getExerciseById(final Long id);
    ExerciseResponseDto createExercise(final ExerciseRequestDto exerciseRequestDto);
    ExerciseResponseDto updateExercise(final Long id, final ExerciseRequestDto exerciseRequestDto);
    void deleteExercise(final Long id);
}
