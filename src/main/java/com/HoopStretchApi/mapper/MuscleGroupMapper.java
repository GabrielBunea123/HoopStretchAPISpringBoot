package com.HoopStretchApi.mapper;

import com.HoopStretchApi.model.dto.muscleGroup.MuscleGroupResponseDto;
import com.HoopStretchApi.model.entity.MuscleGroup;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MuscleGroupMapper {
    MuscleGroupResponseDto toMuscleGroupResponseDto(final MuscleGroup muscleGroup);
}
