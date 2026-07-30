package com.HoopStretchApi.util.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoleEnum {
    SUPER_ADMIN("SUPER_ADMIN"),
    ADMIN("ADMIN"),
    MODERATOR("MODERATOR"),
    PREMIUM("PREMIUM"),
    USER("USER");

    private final String value;
}
