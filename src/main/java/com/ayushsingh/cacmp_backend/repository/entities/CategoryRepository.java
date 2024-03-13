package com.ayushsingh.cacmp_backend.repository.entities;

import com.ayushsingh.cacmp_backend.models.projections.category.CategoryDetailsProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ayushsingh.cacmp_backend.models.entities.Category;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long>{

    Boolean existsByCategoryName(String categoryName);

    @Query("DELETE FROM Category c WHERE c.categoryToken = ?1")
    @Modifying
    void deleteByCategoryToken(String categoryToken);

    @Query("SELECT c FROM Category c WHERE c.categoryToken = ?1")
    Optional<Category> findByCategoryToken(String categoryToken);

    @Query("SELECT c FROM Category c WHERE c.department.deptToken = ?1")
    List<Category> findAllByDepartmentToken(String departmentToken);

    @Query("SELECT c.categoryName as categoryName, c.categoryDescription as categoryDescription, c.department.deptToken as departmentToken, c.department.departmentName FROM Category c WHERE c.categoryToken = ?1")
    CategoryDetailsProjection findCategoryDetailsByCategoryToken(String categoryToken);

    @Query("SELECT COUNT(c) FROM Category c WHERE c.department.deptToken = :departmentToken")
    Long getCategoryCountByDepartmentUsername(@Param("departmentToken")String token);
}
