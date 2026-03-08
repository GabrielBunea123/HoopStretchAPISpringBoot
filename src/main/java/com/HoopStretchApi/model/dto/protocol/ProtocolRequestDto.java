package com.HoopStretchApi.model.dto.protocol;

import com.HoopStretchApi.util.enums.ProtocolTarget;
import com.HoopStretchApi.util.enums.ProtocolPurpose;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProtocolRequestDto {

    private String name;
    @NotNull
    private ProtocolTarget target;
    @NotNull
    private ProtocolPurpose purpose;
    private List<ProtocolExerciseRequestDto> exercises;
}
