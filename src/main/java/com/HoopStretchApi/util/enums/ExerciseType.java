package com.HoopStretchApi.util.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExerciseType {
    STATIC("Static"),
    DYNAMIC("Dynamic"),
    MASSAGE("Massage");

    private final String value;
}
