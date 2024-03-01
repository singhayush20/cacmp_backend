package com.ayushsingh.cacmp_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ayushsingh.cacmp_backend.entity.Category;

public interface CategoryRepository extends JpaRepository<Category,Long>{
    
}
