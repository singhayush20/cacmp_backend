package com.ayushsingh.cacmp_backend.services;

import com.ayushsingh.cacmp_backend.models.roles.UserRole;

public interface UserRoleService {

    public UserRole getUserRoleByRoleName(String roleName);
}
