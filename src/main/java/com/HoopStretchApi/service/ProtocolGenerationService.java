package com.HoopStretchApi.service;

import com.HoopStretchApi.model.dto.protocol.ProtocolGenerationRequestDto;
import com.HoopStretchApi.model.dto.protocol.ProtocolResponseDto;

public interface ProtocolGenerationService {
    ProtocolResponseDto generateProtocol(final String username, final ProtocolGenerationRequestDto protocolGenerationRequestDto);
}
