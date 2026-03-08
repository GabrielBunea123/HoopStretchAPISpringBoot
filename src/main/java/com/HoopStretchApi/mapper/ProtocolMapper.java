package com.HoopStretchApi.mapper;

import com.HoopStretchApi.model.dto.protocol.ProtocolFilterDto;
import com.HoopStretchApi.model.dto.protocol.ProtocolRequestDto;
import com.HoopStretchApi.model.dto.protocol.ProtocolResponseDto;
import com.HoopStretchApi.model.entity.Protocol;
import com.HoopStretchApi.model.entity.User;
import com.HoopStretchApi.util.enums.ProtocolVisibility;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { UserMapper.class, ProtocolExerciseMapper.class })
public interface ProtocolMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "exercises", ignore = true)
    @Mapping(target = "visibility", source = "visibility")
    @Mapping(target = "generated", source = "generated")
    Protocol toProtocol(
            final ProtocolRequestDto dto,
            final ProtocolVisibility visibility,
            final boolean generated,
            final User owner
    );
    ProtocolFilterDto toProtocolFilterDto(final String name, final ProtocolVisibility visibility);

    ProtocolResponseDto toProtocolResponseDto(final Protocol protocol);
}
