package com.ayushsingh.cacmp_backend.repository.filterDto;

import com.ayushsingh.cacmp_backend.models.constants.PublishStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleFilter {
    private String deptToken;
    private PublishStatus publishStatus;
}
