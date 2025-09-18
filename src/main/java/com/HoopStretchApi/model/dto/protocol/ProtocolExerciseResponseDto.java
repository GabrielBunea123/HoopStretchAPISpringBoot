package com.HoopStretchApi.model.dto.protocol;

import com.HoopStretchApi.model.dto.exercise.ExerciseResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProtocolExerciseResponseDto {

    private Long id;
    private Long protocolId;
    private ExerciseResponseDto exercise;
    private int duration;
    private int orderIndex;

}
