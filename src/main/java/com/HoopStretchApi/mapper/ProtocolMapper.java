package com.HoopStretchApi.mapper;

import com.HoopStretchApi.model.dto.protocol.ProtocolRequestDto;
import com.HoopStretchApi.model.dto.protocol.ProtocolResponseDto;
import com.HoopStretchApi.model.entity.Protocol;
import com.HoopStretchApi.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface ProtocolMapper {

    @Mapping(target = "id", ignore = true)
    Protocol toProtocol(final ProtocolRequestDto protocolRequestDto, final User owner);

    ProtocolResponseDto toProtocolResponseDto(final Protocol protocol);
}
