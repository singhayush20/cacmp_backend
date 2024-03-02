package com.ayushsingh.cacmp_backend.repository.entities;

import com.ayushsingh.cacmp_backend.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ayushsingh.cacmp_backend.models.entities.Department;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department,Long> {

    @Query("SELECT d FROM Department d WHERE d.username = ?1")
    Optional<Department> findByUsername(String username);

    Boolean existsByUsername(String username);
}
