package com.ayushsingh.cacmp_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ayushsingh.cacmp_backend.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department,Long> {
    
}
