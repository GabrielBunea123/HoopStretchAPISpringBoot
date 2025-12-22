package com.HoopStretchApi.mapper;

import com.HoopStretchApi.model.dto.protocol.ProtocolFilterDto;
import com.HoopStretchApi.model.dto.protocol.ProtocolRequestDto;
import com.HoopStretchApi.model.dto.protocol.ProtocolResponseDto;
import com.HoopStretchApi.model.entity.Protocol;
import com.HoopStretchApi.model.entity.User;
import com.HoopStretchApi.util.enums.ProtocolVisibility;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface ProtocolMapper {

    @Mapping(target = "id", ignore = true)
    Protocol toProtocol(final ProtocolRequestDto protocolRequestDto, final User owner);
    ProtocolFilterDto toProtocolFilterDto(final String name, final ProtocolVisibility visibility);

    ProtocolResponseDto toProtocolResponseDto(final Protocol protocol);
}
