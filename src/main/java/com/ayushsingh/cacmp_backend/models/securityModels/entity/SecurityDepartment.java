package com.ayushsingh.cacmp_backend.models.securityModels.entity;

import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ayushsingh.cacmp_backend.models.entities.Department;
import com.ayushsingh.cacmp_backend.models.securityModels.authority.DepartmentAuthority;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SecurityDepartment implements UserDetails{
    private final Department department;

     @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return department.getRoles()
                .stream()
                .map(DepartmentAuthority::new)
                .collect(Collectors.toList());

    }

    @Override
    public String getPassword() {
        return this.department.getPassword();
    }

    @Override
    public String getUsername() {
        return this.department.getUsername();
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
