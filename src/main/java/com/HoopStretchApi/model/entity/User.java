package com.HoopStretchApi.model.entity;

import com.HoopStretchApi.exception.ConflictException;
import com.HoopStretchApi.util.enums.OAuthProvider;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Table(name = "users", schema = "public")
@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User extends Auditable implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(length = 150)
    private String displayName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OAuthProvider provider;

    private String password;

    private String dateOfBirth;

    private String profilePictureUrl;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<AccessToken> accessTokens;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<RefreshToken> refreshTokens;

    @Builder.Default
    @OneToMany(
            mappedBy = "user",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<UserMuscle> userMuscles = new ArrayList<>();

    @Builder.Default
    @OneToMany(
            mappedBy = "user",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<UserRole> userRoles = new ArrayList<>();

    public void assignRole(final Role role, @Nullable final LocalDateTime expiresAt) {
        boolean alreadyAssigned = this.userRoles.stream()
                .anyMatch(userRole -> userRole.getRole().equals(role) &&
                        (userRole.getExpiresAt() == null || userRole.getExpiresAt().isAfter(LocalDateTime.now())));

        if (alreadyAssigned){
            throw new ConflictException("Role already assigned");
        };

        final UserRole userRole = new UserRole();
        userRole.setUser(this);
        userRole.setRole(role);
        userRole.setGrantedAt(LocalDateTime.now());
        userRole.setExpiresAt(expiresAt);
        this.userRoles.add(userRole);
    }

    public void addMuscleGroup(final MuscleGroup muscleGroup) {
        final UserMuscle muscle = new UserMuscle();
        muscle.setUser(this);
        muscle.setMuscleGroup(muscleGroup);
        muscle.setPriority(muscleGroup.getDefaultPriorityNumber());
        this.userMuscles.add(muscle);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userRoles.stream()
                .filter(userRole -> userRole.getExpiresAt() == null || userRole.getExpiresAt().isAfter(LocalDateTime.now()))
                .map(userRole -> new SimpleGrantedAuthority(userRole.getRole().getName()))
                .toList();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
