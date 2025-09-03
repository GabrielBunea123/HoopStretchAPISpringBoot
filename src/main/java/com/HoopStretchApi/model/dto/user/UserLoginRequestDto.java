package com.HoopStretchApi.model.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@RequiredArgsConstructor
@Getter
@Setter
public class UserLoginRequestDto {

    @NotNull
    private String username;
    @NotNull
    private String password;
}