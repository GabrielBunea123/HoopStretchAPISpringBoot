package com.HoopStretchApi.mapper;

import com.HoopStretchApi.model.dto.exercise.ExerciseFilterDto;
import com.HoopStretchApi.model.dto.exercise.ExerciseRequestDto;
import com.HoopStretchApi.model.dto.exercise.ExerciseResponseDto;
import com.HoopStretchApi.model.entity.EquipmentItem;
import com.HoopStretchApi.model.entity.Exercise;
import com.HoopStretchApi.model.entity.MuscleGroup;
import com.HoopStretchApi.util.enums.ExerciseType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ExerciseMapper {

    ExerciseResponseDto toExerciseResponseDto(final Exercise exercise);
    Exercise toExercise(final ExerciseRequestDto exerciseRequestDto);
    ExerciseFilterDto toExerciseFilterDto(final String name, final String muscleGroup, final String equipmentItem, final ExerciseType type);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "muscleGroups", source="muscleGroupIds", qualifiedByName = "mapMuscleGroups")
    @Mapping(target = "equipment", source="equipmentIds", qualifiedByName = "mapEquipmentItems")
    void updateExerciseFromDto(final ExerciseRequestDto dto, @MappingTarget final Exercise entity);

    @Named("mapMuscleGroups")
    default Set<MuscleGroup> mapMuscleGroups(final Set<Long> muscleGroupIds) {
        if (muscleGroupIds == null) {
            return Collections.emptySet();
        }
        return muscleGroupIds.stream()
                .map(id -> {
                    final MuscleGroup muscleGroup = new MuscleGroup();
                    muscleGroup.setId(id);
                    return muscleGroup;
                })
                .collect(Collectors.toSet());
    }

    @Named("mapEquipmentItems")
    default Set<EquipmentItem> mapEquipmentItems(final Set<Long> equipmentIds) {
        if (equipmentIds == null) {
            return Collections.emptySet();
        }
        return equipmentIds.stream()
                .map(id -> {
                    final EquipmentItem equipmentItem = new EquipmentItem();
                    equipmentItem.setId(id);
                    return equipmentItem;
                })
                .collect(Collectors.toSet());
    }

}
