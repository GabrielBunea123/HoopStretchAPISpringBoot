package com.HoopStretchApi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.ignoringRequestMatchers("/users/register"))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/**", "/login**", "/swagger-ui/**", "/v3/api-docs/**").permitAll() //modify this after security integration
                        .anyRequest().authenticated()
                );
//                .oauth2Login(oauth2 -> oauth2 //google oauth
//                        .defaultSuccessUrl("/swagger-ui/index.html", true)
//                );
        return http.build();
    }
}