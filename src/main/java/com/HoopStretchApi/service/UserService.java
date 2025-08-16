package com.HoopStretchApi.service;

import com.hoopstretch.openapi.model.UserRegisterRequestDto;
import com.hoopstretch.openapi.model.UserResponseDto;

public interface UserService {
    UserResponseDto getUserById(final Long id);

    void registerUser(final UserRegisterRequestDto userRegisterRequestDto);
}
