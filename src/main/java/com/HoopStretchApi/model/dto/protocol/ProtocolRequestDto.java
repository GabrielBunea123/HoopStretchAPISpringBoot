package com.HoopStretchApi.model.dto.protocol;

import com.HoopStretchApi.util.enums.ProtocolCategory;
import com.HoopStretchApi.util.enums.ProtocolType;
import com.HoopStretchApi.util.enums.ProtocolVisibility;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProtocolRequestDto {

    private String name;
    @NotNull
    private ProtocolCategory category;
    @NotNull
    private ProtocolType type;
    @NotNull
    private ProtocolVisibility visibility;
    private Long ownerId;
}
