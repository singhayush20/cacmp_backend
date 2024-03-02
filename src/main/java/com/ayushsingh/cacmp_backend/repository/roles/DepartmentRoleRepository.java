package com.ayushsingh.cacmp_backend.repository.roles;

import com.ayushsingh.cacmp_backend.models.roles.DepartmentRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DepartmentRoleRepository extends JpaRepository<DepartmentRole, Long> {
    @Query("SELECT d FROM DepartmentRole d WHERE d.roleName = ?1")
    Optional<DepartmentRole> findByRoleName(String roleName);
}
