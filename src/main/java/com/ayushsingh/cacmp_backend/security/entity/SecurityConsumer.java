package com.ayushsingh.cacmp_backend.security.entity;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ayushsingh.cacmp_backend.entity.Consumer;
import com.ayushsingh.cacmp_backend.security.authority.ConsumerAuthority;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SecurityConsumer implements UserDetails {
    private final Consumer consumer;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return consumer.getRoles()
                .stream()
                .map(ConsumerAuthority::new)
                .collect(Collectors.toList());

    }

    @Override
    public String getPassword() {
        return this.consumer.getPassword();
    }

    @Override
    public String getUsername() {
        return this.consumer.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
