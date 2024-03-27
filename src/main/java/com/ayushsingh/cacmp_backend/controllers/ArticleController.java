package com.ayushsingh.cacmp_backend.controllers;

import com.ayushsingh.cacmp_backend.models.constants.PublishStatus;
import com.ayushsingh.cacmp_backend.models.dtos.alertDtos.StatusUpdateDto;
import com.ayushsingh.cacmp_backend.models.dtos.articleDtos.ArticleCreateDto;
import com.ayushsingh.cacmp_backend.models.dtos.articleDtos.ArticleDetailsDto;
import com.ayushsingh.cacmp_backend.models.dtos.articleDtos.ArticleListDto;
import com.ayushsingh.cacmp_backend.models.dtos.articleDtos.ArticleUpdateDto;
import com.ayushsingh.cacmp_backend.repository.filterDto.ArticleFilter;
import com.ayushsingh.cacmp_backend.repository.paginationDto.PaginationDto;
import com.ayushsingh.cacmp_backend.services.ArticleService;
import com.ayushsingh.cacmp_backend.util.responseUtil.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/article")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @PreAuthorize("hasRole('ROLE_DEPARTMENT')")
    @PostMapping("/new")
    public ResponseEntity<ApiResponse<String>> createArticle(@RequestBody ArticleCreateDto articleCreateDto) {
        String token = articleService.createNewArticle(articleCreateDto);
        return new ResponseEntity<>(new ApiResponse<>(token), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_DEPARTMENT')")
    @PostMapping("/upload/images")
    public ResponseEntity<ApiResponse<String>> uploadImages(@RequestParam("token") String articleToken, @RequestPart("images") MultipartFile[] images) {
        return new ResponseEntity<>(new ApiResponse<>(articleService.   uploadImages(articleToken, images)), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_DEPARTMENT')")
    @PostMapping("/upload/videos")
    public ResponseEntity<ApiResponse<String>> uploadVideos(@RequestParam("token") String articleToken, @RequestPart("videos") MultipartFile[] videos) {
        return new ResponseEntity<>(new ApiResponse<>(articleService.uploadVideos(articleToken, videos)), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_DEPARTMENT')")
    @PutMapping("/change-status")
    public ResponseEntity<ApiResponse<String>> changeStatus(@RequestBody StatusUpdateDto statusUpdateDto) {
        return new ResponseEntity<>(new ApiResponse<>(articleService.changeStatus(statusUpdateDto)), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_RESIDENT','ROLE_NON_RESIDENT','ROLE_DEPARTMENT')")
    @GetMapping("/details")
    public ResponseEntity<ApiResponse<ArticleDetailsDto>> getArticleDetails(@RequestParam("token") String articleToken) {
        ArticleDetailsDto articleDetailsDto = articleService.getArticleDetails(articleToken);
        return new ResponseEntity<>(new ApiResponse<>(articleDetailsDto), HttpStatus.OK);
    }

    @GetMapping("/feed")
    public ResponseEntity<ApiResponse<List<ArticleDetailsDto>>> getArticlesList(@RequestBody PaginationDto paginationDto) {
        List<ArticleDetailsDto> articleDetailsDto = articleService.getArticlesList(paginationDto);
        return new ResponseEntity<>(new ApiResponse<>(articleDetailsDto), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_DEPARTMENT')")
    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<ArticleListDto>>> getArticlesList(
            @RequestParam("token") String token,
            @RequestParam(value = "publishStatus",required = false) PublishStatus  publishStatus,
            @RequestParam(value="sortBy",required=false) String sortBy
    ) {
        Sort sort = sortBy != null ? Sort.by(sortBy) : Sort.by("createdAt");
        sort = sort.descending();
        ArticleFilter articleFilter = new ArticleFilter();
        articleFilter.setPublishStatus(publishStatus);
        articleFilter.setDeptToken(token);
        List<ArticleListDto> articleList = articleService.getArticlesListByDepartment(articleFilter,sort);
        return new ResponseEntity<>(new ApiResponse<>(articleList), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse<String>> updateArticle(@RequestBody ArticleUpdateDto articleUpdateDto) {
        return new ResponseEntity<>(new ApiResponse<>(articleService.updateArticle(articleUpdateDto)), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<String>> deleteArticle(@RequestParam("token") String articleToken) {
        return new ResponseEntity<>(new ApiResponse<>(articleService.deleteArticle(articleToken)), HttpStatus.OK);
    }
}
