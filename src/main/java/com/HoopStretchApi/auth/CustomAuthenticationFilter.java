package com.HoopStretchApi.auth;

import com.HoopStretchApi.exception.CustomAuthException;
import com.HoopStretchApi.service.CookieService;
import com.HoopStretchApi.service.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoopstretch.openapi.model.UserLoginRequestDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import java.io.IOException;

public class CustomAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final JwtService jwtService;
    private final CookieService cookieService;

    public CustomAuthenticationFilter(
            final String defaultFilterProcessesUrl,
            final AuthenticationManager authenticationManager,
            final JwtService jwtService,
            final CookieService cookieService) {
        super(defaultFilterProcessesUrl);
        this.jwtService = jwtService;
        this.cookieService = cookieService;
        this.setAuthenticationManager(authenticationManager);
    }


    @Override
    public Authentication attemptAuthentication(final HttpServletRequest request, final HttpServletResponse response) {
        try {
            final UserLoginRequestDto credentials = new ObjectMapper()
                    .readValue(request.getInputStream(), UserLoginRequestDto.class);

            final UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword());

            return getAuthenticationManager().authenticate(authToken);
        } catch (final IOException e) {
            throw new CustomAuthException("Failed to read authentication request");
        }
    }

    @Override
    protected void successfulAuthentication(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain chain,
            final Authentication authResult) throws IOException, ServletException {

        SecurityContextHolder.getContext().setAuthentication(authResult);

        final String token = jwtService.generateToken((UserDetails) authResult.getPrincipal(), 3600 * 1000L);
        final Cookie jwtCookie = cookieService.createJwtCookie(token);
        response.addCookie(jwtCookie);
    }


    @Override
    protected void unsuccessfulAuthentication(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final AuthenticationException failed) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication Failed");
    }
}
