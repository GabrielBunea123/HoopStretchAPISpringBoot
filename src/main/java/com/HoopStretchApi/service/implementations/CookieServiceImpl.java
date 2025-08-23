package com.HoopStretchApi.service.implementations;

import com.HoopStretchApi.config.properties.JwtConfigProperties;
import com.HoopStretchApi.service.CookieService;
import com.HoopStretchApi.util.Constants;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class CookieServiceImpl implements CookieService {

    private final JwtConfigProperties jwtConfigProperties;

    @Override
    public Cookie createJwtCookie(final String token) {
        final Cookie jwtCookie = new Cookie(Constants.ACCESS_TOKEN, token);
        jwtCookie.setHttpOnly(jwtConfigProperties.isJwtCookieHttpOnly());
        jwtCookie.setSecure(jwtConfigProperties.isJwtCookieSecure());
        jwtCookie.setPath(jwtConfigProperties.getJwtAccessTokenCookiePath());
        jwtCookie.setMaxAge(jwtConfigProperties.getAccessExpiration());
        return jwtCookie;
    }

    @Override
    public Cookie clearJwtCookie() {
        final Cookie jwtCookie = new Cookie(Constants.ACCESS_TOKEN, null);
        jwtCookie.setHttpOnly(jwtConfigProperties.isJwtCookieHttpOnly());
        jwtCookie.setSecure(jwtConfigProperties.isJwtCookieSecure());
        jwtCookie.setPath(jwtConfigProperties.getJwtAccessTokenCookiePath());
        jwtCookie.setMaxAge(0);
        return jwtCookie;
    }

    @Override
    public Optional<Cookie> getAccessTokenCookie(final HttpServletRequest request){
        final Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for (final Cookie cookie : cookies) {
                if (cookie.getName().equals(Constants.ACCESS_TOKEN)) {
                    return Optional.of(cookie);
                }
            }
        }
        return Optional.empty();
    }
}
