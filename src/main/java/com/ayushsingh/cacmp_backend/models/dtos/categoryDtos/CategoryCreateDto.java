package com.ayushsingh.cacmp_backend.models.dtos.categoryDtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryCreateDto {


    private String categoryName;


    private String categoryDescription;


    private String departmentToken;
}
