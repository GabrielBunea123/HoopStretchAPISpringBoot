package com.HoopStretchApi.config.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
@Setter
@Getter
@RequiredArgsConstructor
public class JwtConfigProperties {
    private final String secret;
    private final int accessExpiration;
    private final int refreshExpiration;
    private final boolean jwtCookieSecure;
    private final boolean jwtCookieHttpOnly;
    private final String jwtAccessTokenCookiePath;
}
