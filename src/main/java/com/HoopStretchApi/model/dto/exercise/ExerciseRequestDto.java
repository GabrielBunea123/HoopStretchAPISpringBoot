package com.HoopStretchApi.model.dto.exercise;

import com.HoopStretchApi.util.enums.ExerciseType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
public class ExerciseRequestDto {
    @NotNull
    private String name;
    @NotNull
    private boolean isDoubleSided;
    @NotNull
    private Set<Long> muscleGroupIds;
    private Set<Long> equipmentIds;
    @NotNull
    private ExerciseType type;
    @NotNull
    private List<String> guidelines;
    private List<String> contraindications;
    @NotNull
    private boolean isInMobilityTest;
    @NotNull
    private String coverURL;
    @NotNull
    private String videoURL;
}
