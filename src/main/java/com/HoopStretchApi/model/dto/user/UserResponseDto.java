package com.HoopStretchApi.model.dto.user;

import lombok.*;

@RequiredArgsConstructor
@Getter
@Setter
public class UserResponseDto {
    private Long id;
    private String username;
    private String email;
    private String displayName;
}