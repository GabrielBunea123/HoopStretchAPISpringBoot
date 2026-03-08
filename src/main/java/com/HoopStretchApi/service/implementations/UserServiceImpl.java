package com.HoopStretchApi.service.implementations;

import com.HoopStretchApi.exception.ConflictException;
import com.HoopStretchApi.exception.NotFoundException;
import com.HoopStretchApi.mapper.UserMapper;
import com.HoopStretchApi.model.dto.user.UserRegisterRequestDto;
import com.HoopStretchApi.model.dto.user.UserResponseDto;
import com.HoopStretchApi.model.entity.MuscleGroup;
import com.HoopStretchApi.model.entity.User;
import com.HoopStretchApi.repository.MuscleGroupRepository;
import com.HoopStretchApi.repository.UserRepository;
import com.HoopStretchApi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final MuscleGroupRepository muscleGroupRepository;

    @Override
    public UserResponseDto getUserById(final Long id){
        return userRepository.findById(id)
                .map(userMapper::toUserResponseDto)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public User getUserByUsername(final String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("Authenticated user not found"));
    }

    @Override
    public void registerUser(final UserRegisterRequestDto userRegisterRequestDto){
        final User user = userMapper.toUser(userRegisterRequestDto);
        final List<MuscleGroup> muscleGroups = muscleGroupRepository.findAll();
        muscleGroups.forEach(user::addMuscleGroup);
        if(userRepository.existsByEmail(user.getEmail())){
            throw new ConflictException("The email is already in use");
        }
        userRepository.save(user);
    }
}
