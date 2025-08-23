package com.HoopStretchApi.auth;

import com.HoopStretchApi.service.CookieService;
import com.HoopStretchApi.service.CustomUserDetailsService;
import com.HoopStretchApi.service.JwtService;
import com.HoopStretchApi.util.Endpoints;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final HandlerExceptionResolver handlerExceptionResolver;
    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;
    private final CookieService cookieService;

    @Override
    protected void doFilterInternal(@NonNull final HttpServletRequest request, @NonNull final HttpServletResponse response, @NonNull final FilterChain filterChain) throws ServletException, IOException {

        final Optional<Cookie> accessTokenCookie = cookieService.getAccessTokenCookie(request);
        if (accessTokenCookie.isPresent()) {
            final String accessToken = accessTokenCookie.get().getValue();
            try {
                final String username = jwtService.extractUsername(accessToken);

                final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

                if (username != null && (authentication == null || !authentication.isAuthenticated())) {
                    final UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(username);

                    if (jwtService.isTokenValid(accessToken, userDetails)) {
                        final UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }

                filterChain.doFilter(request, response);
            } catch (Exception exception) {
                handlerExceptionResolver.resolveException(request, response, null, exception);
            }
        }
        else{
            filterChain.doFilter(request,response);
        }
    }

    @Override
    protected boolean shouldNotFilter(final HttpServletRequest request) {
        return Endpoints.LOGIN.getValue().equals(request.getServletPath());
    }
}
