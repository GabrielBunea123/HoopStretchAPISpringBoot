package com.HoopStretchApi.mapper;

import com.HoopStretchApi.model.entity.User;
import com.hoopstretch.openapi.model.UserRegisterRequestDto;
import com.hoopstretch.openapi.model.UserResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponseDto toUserResponseDto(final User user);

    @Mapping(target = "password", qualifiedByName = "encodePassword")
    User toUser(final UserRegisterRequestDto userRegisterRequestDto);

    @Named("encodePassword")
    default String encodePassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }
}
