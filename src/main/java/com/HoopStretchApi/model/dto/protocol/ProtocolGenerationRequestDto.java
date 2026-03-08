package com.HoopStretchApi.model.dto.protocol;

import com.HoopStretchApi.util.enums.ProtocolPurpose;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProtocolGenerationRequestDto {
    @NotNull
    private int durationSeconds;
    @NotNull
    private ProtocolPurpose purpose;
}
