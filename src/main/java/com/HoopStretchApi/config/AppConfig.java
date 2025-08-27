package com.HoopStretchApi.config;

import com.HoopStretchApi.config.properties.JwtConfigProperties;
import com.HoopStretchApi.mapper.ExerciseMapper;
import com.HoopStretchApi.mapper.UserMapper;
import com.HoopStretchApi.repository.ExerciseRepository;
import com.HoopStretchApi.repository.UserRepository;
import com.HoopStretchApi.service.*;
import com.HoopStretchApi.service.implementations.CookieServiceImpl;
import com.HoopStretchApi.service.implementations.ExerciseServiceImpl;
import com.HoopStretchApi.service.implementations.JwtServiceImpl;
import com.HoopStretchApi.service.implementations.UserServiceImpl;
import com.HoopStretchApi.specification.ExerciseSpecifications;
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

    @Bean
    public ExerciseSpecifications exerciseSpecifications() {
        return new ExerciseSpecifications();
    }

    @Bean
    public ExerciseService exerciseService(
          final ExerciseRepository exerciseRepository,
          final ExerciseSpecifications exerciseSpecifications,
          final ExerciseMapper exerciseMapper){
        return new ExerciseServiceImpl(
                exerciseRepository,
                exerciseSpecifications,
                exerciseMapper);
    }
}
