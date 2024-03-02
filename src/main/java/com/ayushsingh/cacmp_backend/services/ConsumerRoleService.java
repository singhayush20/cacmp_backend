package com.ayushsingh.cacmp_backend.services;

import com.ayushsingh.cacmp_backend.models.roles.ConsumerRole;

public interface ConsumerRoleService {

    ConsumerRole getConsumerRoleByRoleName(String roleName);
}
