package com.HoopStretchApi.model.dto.protocol;

import com.HoopStretchApi.util.enums.ProtocolVisibility;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ProtocolFilterDto {
    private String name;
    private ProtocolVisibility visibility;
}
