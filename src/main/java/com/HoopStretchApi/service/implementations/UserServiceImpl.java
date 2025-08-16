package com.HoopStretchApi.service.implementations;

import com.HoopStretchApi.mapper.UserMapper;
import com.HoopStretchApi.model.entity.User;
import com.HoopStretchApi.repository.UserRepository;
import com.HoopStretchApi.service.UserService;
import com.hoopstretch.openapi.model.UserRegisterRequestDto;
import com.hoopstretch.openapi.model.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto getUserById(final Long id){
        return userRepository.findById(id)
                .map(userMapper::toUserResponseDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    @Override
    public void registerUser(final UserRegisterRequestDto userRegisterRequestDto){
        final User user = userMapper.toUser(userRegisterRequestDto);
        if(userRepository.existsByEmail(user.getEmail())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
        }
        userRepository.save(user);
    }
}
