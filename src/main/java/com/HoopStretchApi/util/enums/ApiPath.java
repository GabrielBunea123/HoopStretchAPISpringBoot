package com.HoopStretchApi.util.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ApiPath {

    EXERCISES("/exercises/**"),
    USER_PROTOCOLS("/protocols/me/**"),
    PROTOCOL_EXERCISES("/protocol-exercises/**"), //Should be /protocols/exercises/**/
    MOBILITY_ASSESSMENTS("/mobility-assessments/**");

    private final String value;
}
