package com.HoopStretchApi.service.implementations;

import com.HoopStretchApi.mapper.MuscleGroupMapper;
import com.HoopStretchApi.model.dto.muscleGroup.MuscleGroupResponseDto;
import com.HoopStretchApi.repository.MuscleGroupRepository;
import com.HoopStretchApi.service.MuscleGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MuscleGroupServiceImpl implements MuscleGroupService {

    private final MuscleGroupRepository muscleGroupRepository;
    private final MuscleGroupMapper muscleGroupMapper;

    @Override
    public List<MuscleGroupResponseDto> getAllMuscleGroups() {
        return muscleGroupRepository.findAll()
                .stream()
                .map(muscleGroupMapper::toMuscleGroupResponseDto)
                .toList();
    }
}
