package com.HoopStretchApi.model.entity;

import com.HoopStretchApi.enums.OAuthProvider;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(length = 150)
    private String displayName;

    @Column(nullable = false)
    private OAuthProvider provider;

    private String dateOfBirth;

    private String profilePictureUrl;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<AccessToken> accessTokens;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<RefreshToken> refreshTokens;

}
