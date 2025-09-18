package com.HoopStretchApi.mapper;

import com.HoopStretchApi.model.dto.protocol.ProtocolExerciseRequestDto;
import com.HoopStretchApi.model.dto.protocol.ProtocolExerciseResponseDto;
import com.HoopStretchApi.model.entity.Exercise;
import com.HoopStretchApi.model.entity.Protocol;
import com.HoopStretchApi.model.entity.ProtocolExercise;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = { ProtocolMapper.class, ExerciseMapper.class })
public interface ProtocolExerciseMapper {
    @Mapping(target = "protocolId", source = "protocol.id")
    ProtocolExerciseResponseDto toProtocolExerciseResponseDto(final ProtocolExercise exercise);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orderIndex", source = "orderIndex")
    ProtocolExercise toProtocolExercise(final ProtocolExerciseRequestDto protocolExerciseRequestDto, final Protocol protocol, final Exercise exercise, final int orderIndex);

    List<ProtocolExerciseResponseDto> toDtoList(final List<ProtocolExercise> protocolExercises);
}
