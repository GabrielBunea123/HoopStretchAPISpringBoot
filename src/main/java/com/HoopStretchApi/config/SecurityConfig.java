package com.HoopStretchApi.config;

import com.HoopStretchApi.auth.CustomAuthenticationFilter;
import com.HoopStretchApi.auth.JwtAuthenticationFilter;
import com.HoopStretchApi.config.properties.CorsProperties;
import com.HoopStretchApi.service.CookieService;
import com.HoopStretchApi.service.CustomUserDetailsService;
import com.HoopStretchApi.service.JwtService;
import com.HoopStretchApi.service.UserRoleService;
import com.HoopStretchApi.util.enums.ApiPath;
import com.HoopStretchApi.util.enums.Endpoints;
import com.HoopStretchApi.util.enums.RoleEnum;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
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
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public RoleHierarchy roleHierarchy() {
        return RoleHierarchyImpl.withDefaultRolePrefix()
                .role(RoleEnum.SUPER_ADMIN.getValue()).implies(RoleEnum.ADMIN.getValue())
                .role(RoleEnum.ADMIN.getValue()).implies(RoleEnum.MODERATOR.getValue())
                .role(RoleEnum.MODERATOR.getValue()).implies(RoleEnum.PREMIUM.getValue())
                .role(RoleEnum.PREMIUM.getValue()).implies(RoleEnum.USER.getValue())
                .build();
    }

    @Bean
    public SecurityFilterChain filterChain(
            final HttpSecurity http,
            final JwtAuthenticationFilter jwtAuthenticationFilter,
            final CustomAuthenticationFilter customAuthenticationFilter) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(Endpoints.REGISTER.getValue(), Endpoints.LOGIN.getValue(), Endpoints.REFRESH.getValue(), Endpoints.SWAGGER_UI_ALL.getValue(), Endpoints.V3_API_DOCS_ALL.getValue()).permitAll()

                        // exercises
                        .requestMatchers(HttpMethod.POST, ApiPath.EXERCISES.getValue()).hasRole(RoleEnum.ADMIN.getValue())
                        .requestMatchers(HttpMethod.PUT, ApiPath.EXERCISES.getValue()).hasRole(RoleEnum.ADMIN.getValue())
                        .requestMatchers(HttpMethod.DELETE, ApiPath.EXERCISES.getValue()).hasRole(RoleEnum.ADMIN.getValue())
                        .requestMatchers(HttpMethod.GET, ApiPath.EXERCISES.getValue()).hasRole(RoleEnum.USER.getValue())
                        
                        // protocols
                        .requestMatchers(HttpMethod.POST, ApiPath.USER_PROTOCOLS.getValue()).hasRole(RoleEnum.PREMIUM.getValue())
                        .requestMatchers(HttpMethod.PUT, ApiPath.USER_PROTOCOLS.getValue()).hasRole(RoleEnum.PREMIUM.getValue())
                        .requestMatchers(HttpMethod.DELETE, ApiPath.USER_PROTOCOLS.getValue()).hasRole(RoleEnum.PREMIUM.getValue())
                        .requestMatchers(HttpMethod.GET, ApiPath.USER_PROTOCOLS.getValue()).hasRole(RoleEnum.PREMIUM.getValue())
                        
                        // protocol-exercises
                        .requestMatchers(HttpMethod.POST, ApiPath.PROTOCOL_EXERCISES.getValue()).hasRole(RoleEnum.PREMIUM.getValue())
                        .requestMatchers(HttpMethod.PUT, ApiPath.PROTOCOL_EXERCISES.getValue()).hasRole(RoleEnum.PREMIUM.getValue())
                        .requestMatchers(HttpMethod.DELETE, ApiPath.PROTOCOL_EXERCISES.getValue()).hasRole(RoleEnum.PREMIUM.getValue())
                        .requestMatchers(HttpMethod.GET, ApiPath.PROTOCOL_EXERCISES.getValue()).hasRole(RoleEnum.PREMIUM.getValue())

                        // mobility-assessments
                        .requestMatchers(HttpMethod.POST, ApiPath.MOBILITY_ASSESSMENTS.getValue()).hasRole(RoleEnum.USER.getValue())
                        .requestMatchers(HttpMethod.PUT, ApiPath.MOBILITY_ASSESSMENTS.getValue()).hasRole(RoleEnum.USER.getValue())
                        .requestMatchers(HttpMethod.DELETE, ApiPath.MOBILITY_ASSESSMENTS.getValue()).hasRole(RoleEnum.USER.getValue())
                        .requestMatchers(HttpMethod.GET, ApiPath.MOBILITY_ASSESSMENTS.getValue()).hasRole(RoleEnum.USER.getValue())

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
            final CookieService cookieService,
            final UserRoleService userRoleService
    ) {
        return new JwtAuthenticationFilter(
                handlerExceptionResolver,
                jwtService,
                customUserDetailsService,
                cookieService,
                userRoleService);
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