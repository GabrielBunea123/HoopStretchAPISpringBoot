package com.HoopStretchApi.model.dto.exercise;

import com.HoopStretchApi.util.enums.ExerciseType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ExerciseFilterDto {
    private String name;
    private String muscleGroup;
    private String equipmentItem;
    private ExerciseType type;
}
