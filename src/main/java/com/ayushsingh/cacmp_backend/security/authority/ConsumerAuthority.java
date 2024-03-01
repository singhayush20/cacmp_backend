package com.ayushsingh.cacmp_backend.security.authority;

import org.springframework.security.core.GrantedAuthority;

import com.ayushsingh.cacmp_backend.entity.roles.ConsumerRole;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ConsumerAuthority implements GrantedAuthority {

    private final ConsumerRole consumerRole;

    @Override
    public String getAuthority() {
        return this.consumerRole.getRole();
    }

}
