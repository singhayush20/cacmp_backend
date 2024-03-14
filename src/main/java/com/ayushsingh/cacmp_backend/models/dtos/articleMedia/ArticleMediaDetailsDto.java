package com.ayushsingh.cacmp_backend.models.dtos.articleMedia;

import com.ayushsingh.cacmp_backend.models.constants.ArticleMediaType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleMediaDetailsDto {

    private String mediaToken;

    private ArticleMediaType mediaType;

    private String format;

    private String fileName;

    private String url;


}
