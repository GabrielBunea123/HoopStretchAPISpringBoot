package com.HoopStretchApi.service.implementations;

import com.HoopStretchApi.exception.NotFoundException;
import com.HoopStretchApi.mapper.ProtocolMapper;
import com.HoopStretchApi.model.dto.pagination.PaginationRequestDto;
import com.HoopStretchApi.model.dto.pagination.PaginationResponseDto;
import com.HoopStretchApi.model.dto.protocol.ProtocolFilterDto;
import com.HoopStretchApi.model.dto.protocol.ProtocolRequestDto;
import com.HoopStretchApi.model.dto.protocol.ProtocolResponseDto;
import com.HoopStretchApi.model.entity.Protocol;
import com.HoopStretchApi.model.entity.User;
import com.HoopStretchApi.repository.ProtocolRepository;
import com.HoopStretchApi.repository.UserRepository;
import com.HoopStretchApi.service.ProtocolService;
import com.HoopStretchApi.specification.ProtocolSpecifications;
import com.HoopStretchApi.util.PaginationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProtocolServiceImpl implements ProtocolService {

    private final ProtocolRepository protocolRepository;
    private final ProtocolMapper protocolMapper;
    private final UserRepository userRepository;
    private final ProtocolSpecifications protocolSpecifications;

    @Override
    public ProtocolResponseDto createProtocol(final ProtocolRequestDto protocolRequestDto) {
        final User owner = userRepository.findById(protocolRequestDto.getOwnerId())
                .orElseThrow(() -> new NotFoundException("User not found"));
        Protocol protocol = protocolMapper.toProtocol(protocolRequestDto, owner);
        protocol = protocolRepository.save(protocol);

        return protocolMapper.toProtocolResponseDto(protocol);
    }

    @Override
    public ProtocolResponseDto getUserProtocolById(final UserDetails userDetails, final Long id) {
        return userRepository.findByUsername(userDetails.getUsername())
                .map(user -> protocolRepository.findByIdAndOwnerId(id, user.getId())
                        .orElseThrow(() -> new NotFoundException("Protocol not found or not owned by user")))
                .map(protocolMapper::toProtocolResponseDto)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public PaginationResponseDto<ProtocolResponseDto> getUserProtocols(
            final UserDetails userDetails,
            final PaginationRequestDto paginationRequestDto,
            final ProtocolFilterDto protocolFilterDto) {

        final Pageable pageable = PaginationUtils.getPageable(paginationRequestDto);
        final Specification<Protocol> spec = protocolSpecifications.buildFilters(protocolFilterDto);
        // TODO: Add ProtocolVisibility as default filter
        final Page<Protocol> protocolsPage = protocolRepository.findAll(spec, pageable);
        return null;
    }

//    @Override
//    public PaginationResponseDto<ExerciseResponseDto> getExercises(final PaginationRequestDto paginationRequestDto, final ExerciseFilterDto exerciseFilterDto) {
//        final Pageable pageable = PaginationUtils.getPageable(paginationRequestDto);
//        final Specification<Exercise> spec = exerciseSpecifications.buildFilters(exerciseFilterDto);
//        final Page<Exercise> exercisesPage = exerciseRepository.findAll(spec, pageable);
//        final List<ExerciseResponseDto> exercises = exercisesPage.getContent().stream()
//                .map(exerciseMapper::toExerciseResponseDto)
//                .toList();
//
//        return new PaginationResponseDto<>(
//                exercises,
//                exercisesPage.getNumber(),
//                exercisesPage.getSize(),
//                exercisesPage.getTotalElements(),
//                exercisesPage.getTotalPages()
//        );
//    }
}
