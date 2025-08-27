package com.HoopStretchApi.service.implementations;

import com.HoopStretchApi.exception.ConflictException;
import com.HoopStretchApi.exception.NotFoundException;
import com.HoopStretchApi.mapper.UserMapper;
import com.HoopStretchApi.model.dto.user.UserRegisterRequestDto;
import com.HoopStretchApi.model.dto.user.UserResponseDto;
import com.HoopStretchApi.model.entity.User;
import com.HoopStretchApi.repository.UserRepository;
import com.HoopStretchApi.service.UserService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto getUserById(final Long id){
        return userRepository.findById(id)
                .map(userMapper::toUserResponseDto)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public void registerUser(final UserRegisterRequestDto userRegisterRequestDto){
        final User user = userMapper.toUser(userRegisterRequestDto);
        if(userRepository.existsByEmail(user.getEmail())){
            throw new ConflictException("The email is already in use");
        }
        userRepository.save(user);
    }
}
