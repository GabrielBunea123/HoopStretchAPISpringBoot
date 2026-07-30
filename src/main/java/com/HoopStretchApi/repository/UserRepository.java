package com.HoopStretchApi.repository;

import com.HoopStretchApi.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(final String email);
    Optional<User> findByUsername(final String username);
    @Query("""
        SELECT user FROM User user
        LEFT JOIN FETCH user.userRoles userRole
        LEFT JOIN FETCH userRole.role
        WHERE user.username = :username
        """)
    Optional<User> findByUsernameWithRoles(@Param("username") final String username);
}
