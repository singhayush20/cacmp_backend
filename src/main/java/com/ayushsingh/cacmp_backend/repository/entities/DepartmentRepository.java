package com.ayushsingh.cacmp_backend.repository.entities;

import com.ayushsingh.cacmp_backend.models.entities.User;
import com.ayushsingh.cacmp_backend.models.projections.department.DepartmentNameProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ayushsingh.cacmp_backend.models.entities.Department;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department,Long> {

    @Query("SELECT d FROM Department d WHERE d.username = ?1")
    Optional<Department> findByUsername(String username);

    Boolean existsByUsername(String username);

    @Query("SELECT d.deptToken FROM Department d WHERE d.username = ?1")
    String findTokenByUsername(String username);

    @Query("SELECT d FROM Department d WHERE d.deptToken = ?1")
    Optional<Department> findByDepartmentToken(String departmentToken);

    @Query("DELETE FROM Department d WHERE d.deptToken = ?1")
    @Modifying
    void deleteByDeptToken(String departmentToken);

    @Query("SELECT d FROM Department d WHERE d.deptToken = ?1")
    Optional<Department> findByDeptToken(String departmentToken);

    @Query("SELECT d.departmentName as departmentName, d.deptToken as departmentToken FROM Department d")
    List<DepartmentNameProjection> findALlDepartmentNames();
}
