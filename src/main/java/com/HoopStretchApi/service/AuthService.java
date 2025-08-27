package com.HoopStretchApi.service;

import com.HoopStretchApi.model.dto.user.UserLoginRequestDto;
import com.HoopStretchApi.model.dto.user.UserResponseDto;

public interface AuthService {

    UserResponseDto authenticate(final UserLoginRequestDto userLoginRequestDto);
}
