package com.HoopStretchApi.service;

import com.hoopstretch.openapi.model.UserLoginRequestDto;
import com.hoopstretch.openapi.model.UserResponseDto;

public interface AuthService {

    UserResponseDto authenticate(final UserLoginRequestDto userLoginRequestDto);
}
