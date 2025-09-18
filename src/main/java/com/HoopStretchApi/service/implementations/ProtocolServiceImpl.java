package com.HoopStretchApi.service.implementations;

import com.HoopStretchApi.exception.NotFoundException;
import com.HoopStretchApi.mapper.ProtocolMapper;
import com.HoopStretchApi.model.dto.protocol.ProtocolRequestDto;
import com.HoopStretchApi.model.dto.protocol.ProtocolResponseDto;
import com.HoopStretchApi.model.entity.Protocol;
import com.HoopStretchApi.model.entity.User;
import com.HoopStretchApi.repository.ProtocolRepository;
import com.HoopStretchApi.repository.UserRepository;
import com.HoopStretchApi.service.ProtocolService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProtocolServiceImpl implements ProtocolService {

    private final ProtocolRepository protocolRepository;
    private final ProtocolMapper protocolMapper;
    private final UserRepository userRepository;

    @Override
    public ProtocolResponseDto createProtocol(final ProtocolRequestDto protocolRequestDto) {
        final User owner = userRepository.findById(protocolRequestDto.getOwnerId())
                .orElseThrow(() -> new NotFoundException("User not found"));
        Protocol protocol = protocolMapper.toProtocol(protocolRequestDto, owner);
        protocol = protocolRepository.save(protocol);

        return protocolMapper.toProtocolResponseDto(protocol);
    }
}
