package com.HoopStretchApi.service;

import com.HoopStretchApi.model.dto.exercise.ExerciseFilterDto;
import com.HoopStretchApi.model.dto.protocol.ProtocolExerciseRequestDto;
import com.HoopStretchApi.model.dto.protocol.ProtocolExerciseResponseDto;

import java.util.List;

public interface ProtocolExerciseService {
    ProtocolExerciseResponseDto createProtocolExercise(final ProtocolExerciseRequestDto protocolExerciseRequestDto);
    List<ProtocolExerciseResponseDto> getProtocolExercises(final Long protocolId, final ExerciseFilterDto exerciseFilterDto);
    void deleteProtocolExercise(final Long protocolExerciseId);
}
