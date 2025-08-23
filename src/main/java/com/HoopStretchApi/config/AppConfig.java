package com.HoopStretchApi.config;

import com.HoopStretchApi.config.properties.JwtConfigProperties;
import com.HoopStretchApi.mapper.UserMapper;
import com.HoopStretchApi.repository.UserRepository;
import com.HoopStretchApi.service.CookieService;
import com.HoopStretchApi.service.CustomUserDetailsService;
import com.HoopStretchApi.service.JwtService;
import com.HoopStretchApi.service.UserService;
import com.HoopStretchApi.service.implementations.CookieServiceImpl;
import com.HoopStretchApi.service.implementations.JwtServiceImpl;
import com.HoopStretchApi.service.implementations.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public UserService userService(
            final UserRepository userRepository,
            final UserMapper userMapper) {

        return new UserServiceImpl(userRepository, userMapper);
    }

    @Bean
    public CustomUserDetailsService customUserDetailsService(
            final UserRepository userRepository){
        return new CustomUserDetailsService(userRepository);
    }

    @Bean
    public JwtService jwtService(
            final JwtConfigProperties jwtConfigProperties){
        return new JwtServiceImpl(jwtConfigProperties);
    }

    @Bean
    public CookieService cookieService(
            final JwtConfigProperties jwtConfigProperties){
        return new CookieServiceImpl(jwtConfigProperties);
    }
}
