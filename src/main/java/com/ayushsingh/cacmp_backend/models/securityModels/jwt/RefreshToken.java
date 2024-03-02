package com.ayushsingh.cacmp_backend.models.securityModels.jwt;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="refresh_tokens")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "token_type", discriminatorType = DiscriminatorType.STRING)
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long refreshTokenId;

    @Column(name = "token_type", insertable = false,updatable = false)
    private String tokenType;

    @Column(name = "refresh_token", unique = true)
    private String refreshToken;

    @Column(name = "expires_at")
    private Instant expiresAt;
}

