package com.HoopStretchApi.model.dto.muscleGroup;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class MuscleGroupRequestDto {
    @NotNull
    private String name;
    @NotNull
    private int defaultPriorityNumber;
}
