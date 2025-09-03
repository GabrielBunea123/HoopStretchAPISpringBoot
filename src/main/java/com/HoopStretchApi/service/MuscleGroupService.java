package com.HoopStretchApi.service;

import com.HoopStretchApi.model.dto.muscleGroup.MuscleGroupResponseDto;

import java.util.List;

public interface MuscleGroupService {
    List<MuscleGroupResponseDto> getAllMuscleGroups();
}
