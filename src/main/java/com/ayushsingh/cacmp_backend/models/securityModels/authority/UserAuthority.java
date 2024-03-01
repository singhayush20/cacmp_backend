package com.ayushsingh.cacmp_backend.models.securityModels.authority;

import org.springframework.security.core.GrantedAuthority;

import com.ayushsingh.cacmp_backend.models.roles.UserRole;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserAuthority implements GrantedAuthority {
    private final UserRole userRole;

    @Override
    public String getAuthority() {
        return userRole.getRole();
    }
}
