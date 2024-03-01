package com.ayushsingh.cacmp_backend.config.security.authProvider;

import com.ayushsingh.cacmp_backend.config.security.service.DepartmentDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@RequiredArgsConstructor
public class DepartmentAuthProvider implements AuthenticationProvider {

    private final DepartmentDetailsService departmentDetailsService;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}

