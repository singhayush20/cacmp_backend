package com.ayushsingh.cacmp_backend.models.dtos.articleDtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleCreateDto {

    private String title;
    private String content;
    private String departmentToken;
}
