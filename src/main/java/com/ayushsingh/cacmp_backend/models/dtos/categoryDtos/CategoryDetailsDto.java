package com.ayushsingh.cacmp_backend.models.dtos.categoryDtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryDetailsDto {


    private String categoryToken;

    private String categoryName;

    private String categoryDescription;

}
