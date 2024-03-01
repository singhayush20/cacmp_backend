package com.ayushsingh.cacmp_backend.security.authority;

import org.springframework.security.core.GrantedAuthority;

import com.ayushsingh.cacmp_backend.entity.roles.UserRole;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserAuthority implements GrantedAuthority {
    private final UserRole userRole;

    @Override
    public String getAuthority() {
        return userRole.getRole();
    }
}
