package com.ayushsingh.cacmp_backend.models.dtos.articleDtos;

import com.ayushsingh.cacmp_backend.models.constants.PublishStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleListDto {

    private String articleToken;

    private String slug;

    private String title;

    private Date publishedOn;

    private Date createdOn;

    private String description;

    private PublishStatus publishStatus;
}
