package com.HoopStretchApi.controller;

import com.HoopStretchApi.service.UserService;
import com.hoopstretch.openapi.api.UsersApi;
import com.hoopstretch.openapi.model.UserRegisterRequestDto;
import com.hoopstretch.openapi.model.UserResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Tag(name = "UserController")
public class UserController implements UsersApi {

    private final UserService userService;

    @Override
    public ResponseEntity<UserResponseDto> getUserInfo(Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @Override
    public ResponseEntity<Void> registerUser(@Valid @RequestBody final UserRegisterRequestDto userRegisterRequestDto){
        userService.registerUser(userRegisterRequestDto);
        return ResponseEntity.noContent().build();
    }
}