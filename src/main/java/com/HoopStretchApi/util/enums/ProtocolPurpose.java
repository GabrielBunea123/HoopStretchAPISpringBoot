package com.HoopStretchApi.util.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProtocolPurpose {
    PREGAME("Pregame"),
    DAILY("Daily"),
    POSTGAME("Postgame"),
    ASSESSMENT("Assessment");

    private final String value;
}
