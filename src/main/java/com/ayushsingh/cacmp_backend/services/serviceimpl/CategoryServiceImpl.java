package com.ayushsingh.cacmp_backend.services.serviceimpl;

import com.ayushsingh.cacmp_backend.models.dtos.categoryDtos.CategoryCreateDto;
import com.ayushsingh.cacmp_backend.models.dtos.categoryDtos.CategoryDetailsDto;
import com.ayushsingh.cacmp_backend.models.entities.Category;
import com.ayushsingh.cacmp_backend.models.entities.Department;
import com.ayushsingh.cacmp_backend.models.projections.category.CategoryDetailsProjection;
import com.ayushsingh.cacmp_backend.repository.entities.CategoryRepository;
import com.ayushsingh.cacmp_backend.repository.entities.ComplaintRepository;
import com.ayushsingh.cacmp_backend.repository.entities.DepartmentRepository;
import com.ayushsingh.cacmp_backend.services.CategoryService;
import com.ayushsingh.cacmp_backend.util.exceptionUtil.ApiException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final DepartmentRepository departmentRepository;
    private final ModelMapper modelMapper;
    private final ComplaintRepository complaintRepository;
    @Override
    public String createCategory(CategoryCreateDto categoryCreateDto) {
        String categoryName = categoryCreateDto.getCategoryName();
        Boolean isCategoryPresent = categoryRepository.existsByCategoryName(categoryName);
        if (isCategoryPresent) {
            throw new ApiException("Category with name: " + categoryName + " already exists");
        }
        String departmentToken = categoryCreateDto.getDepartmentToken();
        Optional<Department> departmentOptional = departmentRepository.findByDepartmentToken(departmentToken);
        if (departmentOptional.isEmpty()) {
            throw new ApiException("Department with department token: " + departmentToken + " does not exist");
        } else {
            Category category = new Category();
            category.setCategoryName(categoryName);
            category.setCategoryDescription(categoryCreateDto.getCategoryDescription());
            category.setDepartment(departmentOptional.get());
            categoryRepository.save(category);
            return category.getCategoryToken();
        }
    }

    @Override
    @Transactional
    public Long deleteCategory(String categoryToken) {
        Long count=complaintRepository.countComplaintsByCategoryToken(categoryToken);
        if(count!=0){
            throw new ApiException("Category cannot be deleted because there are complaints associated with it");
        }
        categoryRepository.deleteByCategoryToken(categoryToken);
        return categoryRepository.count();
    }

    @Override
    public String updateCategory(CategoryCreateDto categoryCreateDto, String categoryToken) {
        Optional<Category> categoryOptional = categoryRepository.findByCategoryToken(categoryToken);
        if (categoryOptional.isEmpty()) {
            throw new ApiException("Category with category token: " + categoryToken + " does not exist");
        } else {
            String departmentToken = categoryCreateDto.getDepartmentToken();
            Optional<Department> departmentOptional = departmentRepository.findByDepartmentToken(departmentToken);
            if (departmentOptional.isEmpty()) {
                throw new ApiException("Department with department token: " + departmentToken + " does not exist");
            } else {
                Category category = categoryOptional.get();
                category.setCategoryName(categoryCreateDto.getCategoryName());
                category.setCategoryDescription(categoryCreateDto.getCategoryDescription());
                category.setDepartment(departmentOptional.get());
                categoryRepository.save(category);
                return category.getCategoryToken();
            }
        }
    }

    @Override
    public List<CategoryDetailsDto> getAllCategoriesByDepartment(String departmentToken) {
        List<Category> categories=categoryRepository.findAllByDepartmentToken(departmentToken);
        return categories.stream().map(category -> this.modelMapper.map(category,CategoryDetailsDto.class)).toList();
    }

    @Override
    public List<CategoryDetailsDto> getAllCategories() {
        List<Category> categories=categoryRepository.findAll();
        return categories.stream().map(category -> this.modelMapper.map(category,CategoryDetailsDto.class)).toList();
    }

    @Override
    public CategoryDetailsProjection getCategory(String categoryToken) {
        return categoryRepository.findCategoryDetailsByCategoryToken(categoryToken);
    }

}
