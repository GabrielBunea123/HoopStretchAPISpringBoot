package com.HoopStretchApi.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    private LocalDateTime expiresAt;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "refreshToken", cascade = CascadeType.ALL)
    private List<AccessToken> accessTokens;
}
