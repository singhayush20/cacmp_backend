package com.ayushsingh.cacmp_backend.services;

import com.ayushsingh.cacmp_backend.models.roles.UserRole;

import java.util.List;
public interface UserRoleService {

    UserRole getUserRoleByRoleName(String roleName);

    List<String> listUserRoles();
}
