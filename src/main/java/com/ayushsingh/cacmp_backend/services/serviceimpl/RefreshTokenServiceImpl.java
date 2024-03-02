package com.ayushsingh.cacmp_backend.services.serviceimpl;

import com.ayushsingh.cacmp_backend.models.securityModels.jwt.RefreshToken;
import com.ayushsingh.cacmp_backend.repository.jwt.RefreshTokenRepository;
import com.ayushsingh.cacmp_backend.services.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    @Override
    public RefreshToken createRefreshToken(String username) {
        return null;
    }

    @Override
    public Boolean verifyRefreshToken(String refreshToken) {
        return null;
    }
}
