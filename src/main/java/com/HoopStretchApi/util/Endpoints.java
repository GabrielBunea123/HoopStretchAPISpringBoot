package com.HoopStretchApi.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Endpoints {
    LOGIN("/auth/login"),
    REGISTER("/users/register"),
    REFRESH("/auth/refresh"),
    SWAGGER_UI("/swagger-ui/**"),
    V3_API_DOCS("/v3/api-docs/**"),
    ROOT_PATH_ALL("/**");

    private final String value;
}
