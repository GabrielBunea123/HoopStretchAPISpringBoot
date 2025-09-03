package com.HoopStretchApi.model.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class UserRegisterRequestDto {

    @NotNull
    private String username;
    @NotNull
    private String email;
    @NotNull
    private String provider;
    @NotNull
    private String password;
}
