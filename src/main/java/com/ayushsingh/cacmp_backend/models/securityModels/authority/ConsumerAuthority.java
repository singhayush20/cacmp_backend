package com.ayushsingh.cacmp_backend.models.securityModels.authority;

import org.springframework.security.core.GrantedAuthority;

import com.ayushsingh.cacmp_backend.models.roles.ConsumerRole;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ConsumerAuthority implements GrantedAuthority {

    private final ConsumerRole consumerRole;

    @Override
    public String getAuthority() {
        return this.consumerRole.getRoleName();
    }

}
