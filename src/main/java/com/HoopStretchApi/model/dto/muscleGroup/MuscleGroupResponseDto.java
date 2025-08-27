package com.HoopStretchApi.model.dto.muscleGroup;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class MuscleGroupResponseDto {
    private Long id;
    private String name;
}
