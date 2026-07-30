package com.HoopStretchApi.service.implementations;

import com.HoopStretchApi.repository.UserRoleRepository;
import com.HoopStretchApi.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository userRoleRepository;

    @Override
    public Set<String> resolveUserRoles(final Long userId) {
        return userRoleRepository.findActiveRolesByUserId(userId)
                .stream()
                .map(userRole -> userRole.getRole().getName())
                .collect(Collectors.toSet());
    }

    @Override
    public boolean hasRole(final Long userId, final String roleName) {
        return userRoleRepository.hasActiveRole(userId, roleName);
    }
}
