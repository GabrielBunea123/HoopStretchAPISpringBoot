package com.HoopStretchApi.mapper;

import com.HoopStretchApi.model.entity.User;
import com.hoopstretch.openapi.model.UserRegisterRequestDto;
import com.hoopstretch.openapi.model.UserResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponseDto toUserResponseDto(final User user);
    User toUser(final UserRegisterRequestDto userResponseDto);
}
