package com.ayushsingh.cacmp_backend.repository.roles;

import com.ayushsingh.cacmp_backend.models.roles.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    @Query("SELECT u FROM UserRole u WHERE u.roleName = ?1")
    Optional<UserRole> findByRoleName(String roleName);

    @Query("SELECT u.roleName FROM UserRole u")
    List<String> listUserRoles();
}
