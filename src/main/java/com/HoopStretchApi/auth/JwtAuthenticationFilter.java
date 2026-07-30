package com.HoopStretchApi.auth;

import com.HoopStretchApi.exception.UnauthorizedException;
import com.HoopStretchApi.model.entity.User;
import com.HoopStretchApi.service.CookieService;
import com.HoopStretchApi.service.CustomUserDetailsService;
import com.HoopStretchApi.service.JwtService;
import com.HoopStretchApi.service.UserRoleService;
import com.HoopStretchApi.util.Constants;
import com.HoopStretchApi.util.enums.Endpoints;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final HandlerExceptionResolver handlerExceptionResolver;
    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;
    private final CookieService cookieService;
    private final UserRoleService userRoleService;

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
                        final Long userId = ((User) userDetails).getId();
                        final Set<String> roles = userRoleService.resolveUserRoles(userId);
                        final List<SimpleGrantedAuthority> authorities = roles.stream()
                                .map(role -> new SimpleGrantedAuthority(Constants.ROLE_PREFIX + role))
                                .toList();

                        final UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                authorities
                        );

                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }

                filterChain.doFilter(request, response);
            } catch (final Exception exception) {
                logger.error(String.format("Could not authorize user: %s", exception.getMessage()));
                final UnauthorizedException unauthorizedException = new UnauthorizedException("Not authorized to access this resource");
                handlerExceptionResolver.resolveException(request, response, null, unauthorizedException);
            }
        } else{
            filterChain.doFilter(request,response);
        }
    }

    @Override
    protected boolean shouldNotFilter(final HttpServletRequest request) {

        final String path = request.getServletPath();

        return path.startsWith(Endpoints.SWAGGER_UI.getValue())
                || path.startsWith(Endpoints.V3_API_DOCS.getValue())
                || path.equals(Endpoints.LOGIN.getValue());

    }
}
