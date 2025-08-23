package com.HoopStretchApi.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public interface CookieService {
    Cookie createJwtCookie(final String token);
    Cookie clearJwtCookie();
    Optional<Cookie> getAccessTokenCookie(final HttpServletRequest request);
}
