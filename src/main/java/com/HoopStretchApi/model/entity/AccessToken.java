package com.HoopStretchApi.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccessToken extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    private LocalDateTime expiresAt;

    private String tokenType;

    @ManyToOne
    private User user;

    @ManyToOne
    private RefreshToken refreshToken;
}
