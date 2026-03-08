package com.HoopStretchApi.service;

import com.HoopStretchApi.model.dto.user.UserRegisterRequestDto;
import com.HoopStretchApi.model.dto.user.UserResponseDto;
import com.HoopStretchApi.model.entity.User;

public interface UserService {
    UserResponseDto getUserById(final Long id);
    User getUserByUsername(final String username);
    void registerUser(final UserRegisterRequestDto userRegisterRequestDto);
}
