package com.ayushsingh.cacmp_backend.services;

import com.ayushsingh.cacmp_backend.models.securityModels.jwt.RefreshToken;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(String username);

    Boolean verifyRefreshToken(String refreshToken);

}
