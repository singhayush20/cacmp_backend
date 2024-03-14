package com.ayushsingh.cacmp_backend.services;

import com.ayushsingh.cacmp_backend.models.constants.PublishStatus;
import com.ayushsingh.cacmp_backend.models.dtos.alertDtos.StatusUpdateDto;
import com.ayushsingh.cacmp_backend.models.dtos.articleDtos.ArticleCreateDto;
import com.ayushsingh.cacmp_backend.models.dtos.articleDtos.ArticleDetailsDto;
import org.springframework.web.multipart.MultipartFile;

public interface ArticleService {

    String createNewArticle(ArticleCreateDto articleCreateDto);

    String uploadImages(String articleToken, MultipartFile[] images);

    String uploadVideos(String articleToken, MultipartFile[] videos);

    String changeStatus(StatusUpdateDto statusUpdateDto);

    ArticleDetailsDto getArticleDetails(String articleToken);


}
