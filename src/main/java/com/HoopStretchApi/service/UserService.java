package com.HoopStretchApi.service;

import com.HoopStretchApi.model.dto.user.UserRegisterRequestDto;
import com.HoopStretchApi.model.dto.user.UserResponseDto;

public interface UserService {
    UserResponseDto getUserById(final Long id);

    void registerUser(final UserRegisterRequestDto userRegisterRequestDto);
}
