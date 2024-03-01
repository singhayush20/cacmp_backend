package com.ayushsingh.cacmp_backend.config.security.authProvider;

import com.ayushsingh.cacmp_backend.config.security.service.ConsumerDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@RequiredArgsConstructor
public class ConsumerAuthProvider implements AuthenticationProvider {

    private final ConsumerDetailsService consumerDetailsService;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
