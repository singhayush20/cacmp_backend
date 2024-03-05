package com.ayushsingh.cacmp_backend.services;

import com.ayushsingh.cacmp_backend.models.dtos.categoryDtos.CategoryCreateDto;
import com.ayushsingh.cacmp_backend.models.dtos.categoryDtos.CategoryDetailsDto;
import com.ayushsingh.cacmp_backend.models.projections.category.CategoryDetailsProjection;

import java.util.List;

public interface CategoryService {

    String createCategory(CategoryCreateDto categoryCreateDto);

    Long deleteCategory(String categoryToken);

    String updateCategory(CategoryCreateDto categoryCreateDto, String categoryToken);

    List<CategoryDetailsDto> getAllCategoriesByDepartment(String departmentToken);

    List<CategoryDetailsDto> getAllCategories();

    CategoryDetailsProjection getCategory(String categoryToken);
}
