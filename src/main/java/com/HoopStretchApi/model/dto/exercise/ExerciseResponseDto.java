package com.HoopStretchApi.model.dto.exercise;

import com.HoopStretchApi.model.dto.equipmentItem.EquipmentItemResponseDto;
import com.HoopStretchApi.model.dto.muscleGroup.MuscleGroupResponseDto;
import com.HoopStretchApi.util.enums.ExerciseType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
public class ExerciseResponseDto {
    private Long id;
    private String name;
    private boolean isDoubleSided;
    private Set<MuscleGroupResponseDto> muscleGroups;
    private Set<EquipmentItemResponseDto> equipment;
    private ExerciseType type;
    private List<String> guidelines;
    private List<String> contraindications;
    private boolean isInMobilityTest;
    private String coverURL;
    private String videoURL;
}
