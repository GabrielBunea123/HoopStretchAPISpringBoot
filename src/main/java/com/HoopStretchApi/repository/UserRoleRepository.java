package com.HoopStretchApi.repository;

import com.HoopStretchApi.model.entity.User;
import com.HoopStretchApi.model.entity.UserRole;
import com.HoopStretchApi.model.entity.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {

    @Query("""
            SELECT user_role FROM UserRole user_role
            JOIN FETCH user_role.role r
            WHERE user_role.user.id = :userId
            AND (user_role.expiresAt IS NULL OR user_role.expiresAt > CURRENT_TIMESTAMP)
            """)
    List<UserRole> findActiveRolesByUserId(@Param("userId") final Long userId);

    @Query("""
            SELECT CASE WHEN COUNT(user_role) > 0 THEN true ELSE false END
            FROM UserRole user_role
            WHERE user_role.user.id = :userId
            AND user_role.role.name = :roleName
            AND (user_role.expiresAt IS NULL OR user_role.expiresAt > CURRENT_TIMESTAMP)
            """)
    boolean hasActiveRole(@Param("userId") final Long userId, @Param("roleName") final String roleName);

    Long user(User user);
}
