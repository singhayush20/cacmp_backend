package com.ayushsingh.cacmp_backend.controllers;

import com.ayushsingh.cacmp_backend.models.constants.PublishStatus;
import com.ayushsingh.cacmp_backend.models.dtos.alertDtos.StatusUpdateDto;
import com.ayushsingh.cacmp_backend.models.dtos.articleDtos.ArticleCreateDto;
import com.ayushsingh.cacmp_backend.models.dtos.articleDtos.ArticleDetailsDto;
import com.ayushsingh.cacmp_backend.services.ArticleService;
import com.ayushsingh.cacmp_backend.util.responseUtil.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/article")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping("/new")
    public ResponseEntity<ApiResponse<String>> createArticle(@RequestBody ArticleCreateDto articleCreateDto) {
        String token = articleService.createNewArticle(articleCreateDto);
        return new ResponseEntity<>(new ApiResponse<>(token), HttpStatus.CREATED);
    }

    @PostMapping("/upload/images")
    public ResponseEntity<ApiResponse<String>> uploadImages(@RequestParam("token") String articleToken, @RequestPart("images") MultipartFile[] images) {
        return new ResponseEntity<>(new ApiResponse<>(articleService.uploadImages(articleToken, images)), HttpStatus.CREATED);
    }

    @PostMapping("/upload/videos")
    public ResponseEntity<ApiResponse<String>> uploadVideos(@RequestParam("token") String articleToken, @RequestPart("videos") MultipartFile[] videos) {
        return new ResponseEntity<>(new ApiResponse<>(articleService.uploadVideos(articleToken, videos)), HttpStatus.CREATED);
    }

    @PutMapping("/change-status")
    public ResponseEntity<ApiResponse<String>> changeStatus(@RequestBody StatusUpdateDto statusUpdateDto) {
        return new ResponseEntity<>(new ApiResponse<>(articleService.changeStatus(statusUpdateDto)), HttpStatus.OK);
    }

    @GetMapping("/details")
    public ResponseEntity<ApiResponse<ArticleDetailsDto>> getArticleDetails(@RequestParam("token") String articleToken) {
        ArticleDetailsDto articleDetailsDto = articleService.getArticleDetails(articleToken);
        return new ResponseEntity<>(new ApiResponse<>(articleDetailsDto), HttpStatus.OK);
    }
}
