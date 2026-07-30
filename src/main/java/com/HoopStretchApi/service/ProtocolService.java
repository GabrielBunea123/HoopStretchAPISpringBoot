package com.HoopStretchApi.service;

import com.HoopStretchApi.model.dto.pagination.PaginationRequestDto;
import com.HoopStretchApi.model.dto.pagination.PaginationResponseDto;
import com.HoopStretchApi.model.dto.protocol.ProtocolFilterDto;
import com.HoopStretchApi.model.dto.protocol.ProtocolRequestDto;
import com.HoopStretchApi.model.dto.protocol.ProtocolResponseDto;
import com.HoopStretchApi.model.dto.protocol.UpdateUserProtocolRequestDto;

import java.util.List;

public interface ProtocolService {
    ProtocolResponseDto createPublicProtocol(final ProtocolRequestDto protocolRequestDto);
    ProtocolResponseDto createUserProtocol(final ProtocolRequestDto protocolRequestDto, final String username);
    ProtocolResponseDto createMobilityTestProtocol(final List<Long> exerciseIds);
    ProtocolResponseDto getUserProtocolById(final String username, final Long id);
    PaginationResponseDto<ProtocolResponseDto> getUserProtocols(
            final String username,
            final PaginationRequestDto paginationRequestDto,
            final ProtocolFilterDto protocolFilterDto);
    ProtocolResponseDto copyProtocolIntoUserProtocol(final String username, final Long existingProtocolId);
    ProtocolResponseDto updateUserProtocol(final String username, final Long protocolId, final UpdateUserProtocolRequestDto updateUserProtocolRequestDto);
}
