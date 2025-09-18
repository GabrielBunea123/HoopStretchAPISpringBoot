package com.HoopStretchApi.util.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProtocolType {
    PREGAME("Pregame"),
    DAILY("Daily"),
    POSTGAME("Postgame");

    private final String value;
}
