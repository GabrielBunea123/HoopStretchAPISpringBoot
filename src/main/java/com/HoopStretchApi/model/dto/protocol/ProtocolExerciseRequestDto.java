package com.HoopStretchApi.model.dto.protocol;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProtocolExerciseRequestDto {
    private Long protocolId;
    private Long exerciseId;
    private int duration;
}
