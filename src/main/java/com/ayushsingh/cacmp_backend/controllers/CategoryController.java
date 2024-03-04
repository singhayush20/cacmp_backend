package com.ayushsingh.cacmp_backend.controllers;

import com.ayushsingh.cacmp_backend.models.dtos.categoryDtos.CategoryCreateDto;
import com.ayushsingh.cacmp_backend.models.dtos.categoryDtos.CategoryDetailsDto;
import com.ayushsingh.cacmp_backend.services.CategoryService;
import com.ayushsingh.cacmp_backend.util.responseUtil.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/new")
    public ResponseEntity<ApiResponse<String>> createCategory(@RequestBody CategoryCreateDto categoryCreateDto){
        String token=categoryService.createCategory(categoryCreateDto);
        return new ResponseEntity<>(new ApiResponse<>(token), HttpStatus.CREATED);
    }

    @PutMapping("")
    public ResponseEntity<ApiResponse<String>> updateCategory(@RequestBody CategoryCreateDto categoryCreateDto, @RequestParam(name = "categoryToken") String token){
        String categoryToken=categoryService.updateCategory(categoryCreateDto,token);
        return new ResponseEntity<>(new ApiResponse<>(categoryToken),HttpStatus.OK);
    }

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
}
