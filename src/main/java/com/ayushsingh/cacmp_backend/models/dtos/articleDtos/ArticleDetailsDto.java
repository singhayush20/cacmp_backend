package com.ayushsingh.cacmp_backend.models.dtos.articleDtos;

import com.ayushsingh.cacmp_backend.models.constants.PublishStatus;
import com.ayushsingh.cacmp_backend.models.dtos.articleMedia.ArticleMediaDetailsDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDetailsDto {

    private String articleToken;

    private String title;

    private String content;

    private PublishStatus publishStatus;

    private Date publishDate;

    private String departmentName;

    private List<ArticleMediaDetailsDto> articleMedia=new ArrayList<>();

}
