package com.ayushsingh.cacmp_backend.repository;

import com.ayushsingh.cacmp_backend.models.roles.ConsumerRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ConsumerRoleRepository extends JpaRepository<ConsumerRoleRepository, Long> {
    @Query("SELECT u FROM ConsumerRole u WHERE u.roleName = ?1")
    Optional<ConsumerRole> findByRoleName(String roleName);
}
