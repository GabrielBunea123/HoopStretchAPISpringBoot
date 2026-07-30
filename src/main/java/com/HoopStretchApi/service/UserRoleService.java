package com.HoopStretchApi.service;

import java.util.Set;

public interface UserRoleService {
    Set<String> resolveUserRoles(final Long userId);

    boolean hasRole(final Long userId, final String roleName);
}
