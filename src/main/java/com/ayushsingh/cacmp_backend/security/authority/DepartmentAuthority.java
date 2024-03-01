package com.ayushsingh.cacmp_backend.security.authority;

import org.springframework.security.core.GrantedAuthority;

import com.ayushsingh.cacmp_backend.entity.roles.DepartmentRole;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DepartmentAuthority implements GrantedAuthority {
    private final DepartmentRole departmentRole;

    @Override
    public String getAuthority() {
        return departmentRole.getRole();
    }
}
