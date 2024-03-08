package com.ayushsingh.cacmp_backend.controllers;

import com.ayushsingh.cacmp_backend.models.dtos.categoryDtos.CategoryCreateDto;
import com.ayushsingh.cacmp_backend.models.dtos.categoryDtos.CategoryDetailsDto;
import com.ayushsingh.cacmp_backend.models.projections.category.CategoryDetailsProjection;
import com.ayushsingh.cacmp_backend.services.CategoryService;
import com.ayushsingh.cacmp_backend.util.responseUtil.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PreAuthorize("hasAnyRole('ROLE_ROOT_ADMIN', 'ROLE_SUB_ADMIN')")
    @PostMapping("/new")
    public ResponseEntity<ApiResponse<String>> createCategory(@RequestBody CategoryCreateDto categoryCreateDto){
        String token=categoryService.createCategory(categoryCreateDto);
        return new ResponseEntity<>(new ApiResponse<>(token), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ROLE_ROOT_ADMIN', 'ROLE_SUB_ADMIN')")
    @PutMapping("/{categoryToken}")
    public ResponseEntity<ApiResponse<String>> updateCategory(@RequestBody CategoryCreateDto categoryCreateDto, @PathVariable(name = "categoryToken") String token){
        String categoryToken=categoryService.updateCategory(categoryCreateDto,token);
        return new ResponseEntity<>(new ApiResponse<>(categoryToken),HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ROOT_ADMIN', 'ROLE_SUB_ADMIN')")
    @DeleteMapping("/{categoryToken}")
    public ResponseEntity<ApiResponse<Long>> deleteCategory(@PathVariable(name = "categoryToken") String token){
        Long count=categoryService.deleteCategory(token);
        return new ResponseEntity<>(new ApiResponse<>(count),HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<CategoryDetailsDto>>> getAllCategoriesByDepartment(@RequestParam(name = "departmentToken") String departmentToken){
        List<CategoryDetailsDto> categories=categoryService.getAllCategoriesByDepartment(departmentToken);
        return new ResponseEntity<>(new ApiResponse<>(categories),HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<CategoryDetailsDto>>> getAllCategories(){
        List<CategoryDetailsDto> category=categoryService.getAllCategories();
        return new ResponseEntity<>(new ApiResponse<>(category),HttpStatus.OK);
    }

    @GetMapping("/{categoryToken}")
    public ResponseEntity<ApiResponse<CategoryDetailsProjection>> getCategory(@PathVariable(name = "categoryToken") String token){

        CategoryDetailsProjection category=categoryService.getCategory(token);
        return new ResponseEntity<>(new ApiResponse<>(category),HttpStatus.OK);
    }
}
