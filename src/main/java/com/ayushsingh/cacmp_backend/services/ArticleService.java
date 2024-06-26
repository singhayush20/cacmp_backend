package com.ayushsingh.cacmp_backend.services;

import com.ayushsingh.cacmp_backend.models.dtos.alertDtos.StatusUpdateDto;
import com.ayushsingh.cacmp_backend.models.dtos.articleDtos.ArticleCreateDto;
import com.ayushsingh.cacmp_backend.models.dtos.articleDtos.ArticleDetailsDto;
import com.ayushsingh.cacmp_backend.models.dtos.articleDtos.ArticleListDto;
import com.ayushsingh.cacmp_backend.models.dtos.articleDtos.ArticleUpdateDto;
import com.ayushsingh.cacmp_backend.repository.filterDto.ArticleFilter;
import com.ayushsingh.cacmp_backend.repository.paginationDto.PaginationDto;
import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ArticleService {

    String createNewArticle(ArticleCreateDto articleCreateDto);

    String uploadImages(String articleToken, MultipartFile[] images);

    String uploadVideos(String articleToken, MultipartFile[] videos);

    String changeStatus(StatusUpdateDto statusUpdateDto);

    ArticleDetailsDto getArticleDetails(String articleToken);

    List<ArticleDetailsDto> getArticlesList(PaginationDto paginationDto);

    List<ArticleListDto> getArticlesListByDepartment(ArticleFilter articleFilter, Sort sort);

    String updateArticle(ArticleUpdateDto articleUpdateDto);

    String deleteArticle(String articleToken);

    ArticleDetailsDto getArticleDetailsBySlug(String slug);
}
