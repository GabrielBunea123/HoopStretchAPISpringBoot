package com.HoopStretchApi.util.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProtocolVisibility {
    GENERAL("General"),
    USER("User");

    private final String value;
}
