package com.HoopStretchApi.repository;

import com.HoopStretchApi.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(final String name);
}
