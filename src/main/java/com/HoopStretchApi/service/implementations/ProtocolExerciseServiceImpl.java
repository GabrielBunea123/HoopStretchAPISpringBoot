package com.HoopStretchApi.service.implementations;

import com.HoopStretchApi.exception.NotFoundException;
import com.HoopStretchApi.mapper.ProtocolExerciseMapper;
import com.HoopStretchApi.model.dto.exercise.ExerciseFilterDto;
import com.HoopStretchApi.model.dto.protocol.ProtocolExerciseRequestDto;
import com.HoopStretchApi.model.dto.protocol.ProtocolExerciseResponseDto;
import com.HoopStretchApi.model.entity.Exercise;
import com.HoopStretchApi.model.entity.Protocol;
import com.HoopStretchApi.model.entity.ProtocolExercise;
import com.HoopStretchApi.repository.ExerciseRepository;
import com.HoopStretchApi.repository.ProtocolExerciseRepository;
import com.HoopStretchApi.repository.ProtocolRepository;
import com.HoopStretchApi.service.ProtocolExerciseService;
import com.HoopStretchApi.specification.ProtocolExerciseSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProtocolExerciseServiceImpl implements ProtocolExerciseService {

    final ProtocolExerciseMapper protocolExerciseMapper;
    final ProtocolExerciseRepository protocolExerciseRepository;
    final ProtocolRepository protocolRepository;
    final ExerciseRepository exerciseRepository;
    final ProtocolExerciseSpecifications protocolExerciseSpecifications;

    @Override
    public ProtocolExerciseResponseDto createProtocolExercise(final ProtocolExerciseRequestDto protocolExerciseRequestDto) {
        final Protocol protocol = protocolRepository.findById(protocolExerciseRequestDto.getProtocolId())
                .orElseThrow(() -> new NotFoundException("Protocol not found"));
        final Exercise exercise = exerciseRepository.findById(protocolExerciseRequestDto.getExerciseId())
                .orElseThrow(() -> new NotFoundException("Exercise not found"));

        int orderIndex = protocol.getExercises().size();

        final ProtocolExercise protocolExercise = protocolExerciseMapper.toProtocolExercise(protocolExerciseRequestDto, protocol, exercise, orderIndex);
        final ProtocolExercise savedProtocolExercise = protocolExerciseRepository.save(protocolExercise);
        return protocolExerciseMapper.toProtocolExerciseResponseDto(savedProtocolExercise);
    }

    @Override
    public List<ProtocolExerciseResponseDto> getProtocolExercises(final Long protocolId, final ExerciseFilterDto exerciseFilterDto){
        final Specification<ProtocolExercise> spec = protocolExerciseSpecifications.buildFilters(protocolId, exerciseFilterDto);
        final List<ProtocolExercise> protocolExercises = protocolExerciseRepository.findAll(spec);
        return protocolExerciseMapper.toDtoList(protocolExercises);
    }

    @Override
    public void deleteProtocolExercise(final Long id) {
        final ProtocolExercise protocolExercise = protocolExerciseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Protocol exercise not found"));
        protocolExerciseRepository.delete(protocolExercise);
    }

}
