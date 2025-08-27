package com.HoopStretchApi.mapper;

import com.HoopStretchApi.model.dto.exercise.ExerciseFilterDto;
import com.HoopStretchApi.model.dto.exercise.ExerciseResponseDto;
import com.HoopStretchApi.model.entity.Exercise;
import com.HoopStretchApi.util.enums.ExerciseType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ExerciseMapper {

    ExerciseResponseDto toExerciseResponseDto(final Exercise exercise);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "muscleGroup", source = "muscleGroup")
    @Mapping(target = "equipmentItem", source = "equipmentItem")
    @Mapping(target = "type", source = "type")
    ExerciseFilterDto toExerciseFilterDto(final String name, final String muscleGroup, final String equipmentItem, final ExerciseType type);

}
