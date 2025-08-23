package com.HoopStretchApi.config;

import com.HoopStretchApi.auth.CustomAuthenticationFilter;
import com.HoopStretchApi.auth.JwtAuthenticationFilter;
import com.HoopStretchApi.config.properties.CorsProperties;
import com.HoopStretchApi.service.CookieService;
import com.HoopStretchApi.service.CustomUserDetailsService;
import com.HoopStretchApi.service.JwtService;
import com.HoopStretchApi.util.Endpoints;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(
            final HttpSecurity http,
            final JwtAuthenticationFilter jwtAuthenticationFilter,
            final CustomAuthenticationFilter customAuthenticationFilter) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(Endpoints.REGISTER.getValue(), Endpoints.LOGIN.getValue(), Endpoints.REFRESH.getValue(), Endpoints.SWAGGER_UI.getValue(), Endpoints.V3_API_DOCS.getValue()).permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterAt(customAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public CustomAuthenticationFilter customAuthenticationFilter(
            final AuthenticationManager authenticationManager,
            final JwtService jwtService,
            final CookieService cookieService) {
        return new CustomAuthenticationFilter(Endpoints.LOGIN.getValue(), authenticationManager, jwtService, cookieService);
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(
            final HandlerExceptionResolver handlerExceptionResolver,
            final JwtService jwtService,
            final CustomUserDetailsService customUserDetailsService,
            final CookieService cookieService) {
        return new JwtAuthenticationFilter(
                handlerExceptionResolver,
                jwtService,
                customUserDetailsService,
                cookieService);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(
            final CorsProperties corsProperties
    ) {
        final CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(corsProperties.getAllowedOrigins());
        configuration.setAllowedMethods(corsProperties.getAllowedMethods());
        configuration.setAllowedHeaders(corsProperties.getAllowedHeaders());

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration(Endpoints.ROOT_PATH_ALL.getValue(),configuration);

        return source;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(final AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}