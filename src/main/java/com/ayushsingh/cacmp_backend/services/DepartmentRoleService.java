package com.ayushsingh.cacmp_backend.services;

import com.ayushsingh.cacmp_backend.models.roles.DepartmentRole;

import java.util.Set;

public interface DepartmentRoleService {

    DepartmentRole getDepartmentRoleByRoleName(String roleName);

}
