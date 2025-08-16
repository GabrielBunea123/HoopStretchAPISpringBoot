package com.HoopStretchApi.config;

import com.HoopStretchApi.mapper.UserMapper;
import com.HoopStretchApi.repository.UserRepository;
import com.HoopStretchApi.service.UserService;
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
}
