package com.HoopStretchApi.mapper;

import com.HoopStretchApi.model.dto.protocol.ProtocolExerciseResponseDto;
import com.HoopStretchApi.model.entity.Exercise;
import com.HoopStretchApi.model.entity.Protocol;
import com.HoopStretchApi.model.entity.ProtocolExercise;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = { ProtocolMapper.class, ExerciseMapper.class })
public interface ProtocolExerciseMapper {
    @Mapping(target = "protocolId", source = "exercise.protocol.id")
    ProtocolExerciseResponseDto toProtocolExerciseResponseDto(final ProtocolExercise exercise);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orderIndex", source = "orderIndex")
    ProtocolExercise toProtocolExercise(final int duration, final Protocol protocol, final Exercise exercise, final int orderIndex);

    List<ProtocolExerciseResponseDto> toProtocolExerciseResponseListDto(final List<ProtocolExercise> protocolExercises);
}
