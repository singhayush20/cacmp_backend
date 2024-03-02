package com.ayushsingh.cacmp_backend.repository;

import com.ayushsingh.cacmp_backend.models.roles.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    @Query("SELECT u FROM UserRole u WHERE u.roleName = ?1")
    Optional<UserRole> findByRoleName(String roleName);
}
