package com.HoopStretchApi.model.dto.protocol;

import com.HoopStretchApi.util.enums.ProtocolPurpose;
import com.HoopStretchApi.util.enums.ProtocolTarget;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpdateUserProtocolRequestDto {
    private String name;
    @NotNull
    private ProtocolTarget target;
    @NotNull
    private ProtocolPurpose purpose;
}
