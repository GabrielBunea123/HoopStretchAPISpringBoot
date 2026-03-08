package com.HoopStretchApi.util.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProtocolVisibility {
    PUBLIC("public"),
    PRIVATE("private");

    private final String value;
    public static final String DEFAULT_PROTOCOL_VISIBILITY = "PRIVATE";
}
