package com.HoopStretchApi.service;

import com.HoopStretchApi.model.dto.protocol.ProtocolRequestDto;
import com.HoopStretchApi.model.dto.protocol.ProtocolResponseDto;

public interface ProtocolService {
    ProtocolResponseDto createProtocol(final ProtocolRequestDto protocolRequestDto);
}
