package com.HoopStretchApi.service;

import com.HoopStretchApi.model.dto.pagination.PaginationRequestDto;
import com.HoopStretchApi.model.dto.pagination.PaginationResponseDto;
import com.HoopStretchApi.model.dto.protocol.ProtocolFilterDto;
import com.HoopStretchApi.model.dto.protocol.ProtocolRequestDto;
import com.HoopStretchApi.model.dto.protocol.ProtocolResponseDto;
import org.springframework.security.core.userdetails.UserDetails;

public interface ProtocolService {
    ProtocolResponseDto createProtocol(final ProtocolRequestDto protocolRequestDto);
    ProtocolResponseDto getUserProtocolById(final UserDetails userDetails, final Long id);
    PaginationResponseDto<ProtocolResponseDto> getUserProtocols(
            final UserDetails userDetails,
            final PaginationRequestDto paginationRequestDto,
            final ProtocolFilterDto protocolFilterDto);
}
