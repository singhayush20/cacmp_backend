package com.ayushsingh.cacmp_backend.services.serviceimpl;

import com.ayushsingh.cacmp_backend.models.constants.ArticleMediaType;
import com.ayushsingh.cacmp_backend.models.constants.PublishStatus;
import com.ayushsingh.cacmp_backend.models.dtos.alertDtos.StatusUpdateDto;
import com.ayushsingh.cacmp_backend.models.dtos.articleDtos.ArticleCreateDto;
import com.ayushsingh.cacmp_backend.models.dtos.articleDtos.ArticleDetailsDto;
import com.ayushsingh.cacmp_backend.models.dtos.articleDtos.ArticleListDto;
import com.ayushsingh.cacmp_backend.models.dtos.articleMedia.ArticleMediaDetailsDto;
import com.ayushsingh.cacmp_backend.models.entities.Article;
import com.ayushsingh.cacmp_backend.models.entities.ArticleMedia;
import com.ayushsingh.cacmp_backend.models.entities.Department;
import com.ayushsingh.cacmp_backend.repository.entities.ArticleMediaRepository;
import com.ayushsingh.cacmp_backend.repository.entities.ArticleRepository;
import com.ayushsingh.cacmp_backend.repository.entities.DepartmentRepository;
import com.ayushsingh.cacmp_backend.repository.filterDto.ArticleFilter;
import com.ayushsingh.cacmp_backend.repository.filterDto.ArticlePaginationDto;
import com.ayushsingh.cacmp_backend.repository.specifications.article.ArticleSpecification;
import com.ayushsingh.cacmp_backend.services.ArticleService;
import com.ayushsingh.cacmp_backend.util.exceptionUtil.ApiException;
import com.ayushsingh.cacmp_backend.util.imageUtil.ImageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;


@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    private final DepartmentRepository departmentRepository;

    private final ArticleMediaRepository articleMediaRepository;

    private final ImageService imageService;

    @Override
    public String createNewArticle (ArticleCreateDto articleCreateDto) {
        Department department = this.departmentRepository.findByDepartmentToken(articleCreateDto.getDepartmentToken()).orElseThrow(() -> new ApiException("Department not found"));
        Article article = new Article();
        article.setDepartment(department);
        article.setTitle(articleCreateDto.getTitle());
        article.setContent(articleCreateDto.getContent());
        article.setPublishStatus(PublishStatus.DRAFT);
        return articleRepository.save(article).getArticleToken();
    }

    @Transactional
    @Override
    public String uploadImages (String articleToken, MultipartFile[] images) {
        Article article = this.articleRepository.findByArticleToken(articleToken).orElseThrow(() -> new ApiException("Article not found"));
        for (MultipartFile image : images) {
            Map<String, Object> uploadResult = imageService.uploadArticleImage(image);
            ArticleMedia articleMedia = new ArticleMedia();
            articleMedia.setArticle(article);
            articleMedia.setMediaType(ArticleMediaType.IMAGE);
            articleMedia.setFormat(image.getContentType());
            articleMedia.setUrl(uploadResult.get("public_url").toString());
            articleMedia.setFileName(uploadResult.get("original_filename").toString());
            articleMediaRepository.save(articleMedia);
        }
        return articleToken;
    }

    @Override
    public String uploadVideos (String articleToken, MultipartFile[] videos) {
        Article article = this.articleRepository.findByArticleToken(articleToken).orElseThrow(() -> new ApiException("Article not found"));
        for (MultipartFile video : videos) {
            Map<String, Object> uploadResult = imageService.uploadArticleImage(video);
            ArticleMedia articleMedia = new ArticleMedia();
            articleMedia.setArticle(article);
            articleMedia.setMediaType(ArticleMediaType.IMAGE);
            articleMedia.setFormat(video.getContentType());
            articleMedia.setUrl(uploadResult.get("public_url").toString());
            articleMedia.setFileName(uploadResult.get("original_filename").toString());
            articleMediaRepository.save(articleMedia);
        }
        return articleToken;
    }

    @Override
    public String changeStatus (StatusUpdateDto statusUpdateDto) {
        Article article = this.articleRepository.findByArticleToken(statusUpdateDto.getToken()).orElseThrow(() -> new ApiException("Article not found"));
        PublishStatus currentStatus = article.getPublishStatus();
        PublishStatus publishStatus = statusUpdateDto.getPublishStatus();
        if (publishStatus == currentStatus) {
            throw new ApiException("Current and new status cannot be same " + publishStatus);
        }
        if (publishStatus == PublishStatus.DRAFT) {
            article.setPublishStatus(PublishStatus.DRAFT);
            article.setPublishDate(null);
        } else if (publishStatus == PublishStatus.PUBLISHED && currentStatus == PublishStatus.DRAFT) {
            article.setPublishStatus(PublishStatus.PUBLISHED);
            article.setPublishDate(new Date());
        } else if (publishStatus == PublishStatus.ARCHIVED) {
            article.setPublishStatus(PublishStatus.ARCHIVED);
        }
        return articleRepository.save(article).getArticleToken();
    }

    @Override
    public ArticleDetailsDto getArticleDetails (String articleToken) {
        Article article = this.articleRepository.findByArticleToken(articleToken).orElseThrow(() -> new ApiException("Article not found"));
        ArticleDetailsDto articleDetailsDto = new ArticleDetailsDto();
        articleDetailsDto.setArticleToken(article.getArticleToken());
        articleDetailsDto.setTitle(article.getTitle());
        articleDetailsDto.setContent(article.getContent());
        articleDetailsDto.setPublishStatus(article.getPublishStatus());
        articleDetailsDto.setPublishDate(article.getPublishDate());
        String departmentName = this.articleRepository.findDepartmentNameByToken(articleToken);
        articleDetailsDto.setDepartmentName(departmentName);
        List<ArticleMediaDetailsDto> articleMedia = new ArrayList<>();
        Set<ArticleMedia> articleMediaSet = article.getArticleMedia();
        for (ArticleMedia articleMedia1 : articleMediaSet) {
            ArticleMediaDetailsDto articleMediaDetailsDto = new ArticleMediaDetailsDto();
            articleMediaDetailsDto.setUrl(articleMedia1.getUrl());
            articleMediaDetailsDto.setFileName(articleMedia1.getFileName());
            articleMediaDetailsDto.setFormat(articleMedia1.getFormat());
            articleMediaDetailsDto.setMediaType(articleMedia1.getMediaType());
            articleMedia.add(articleMediaDetailsDto);
        }
        articleDetailsDto.setArticleMedia(articleMedia);
        return articleDetailsDto;
    }

    @Override
    public List<ArticleDetailsDto> getArticlesList (ArticlePaginationDto articlePaginationDto) {
        Sort sort = null;
        if (articlePaginationDto.getSortBy() != null) {
            sort = Sort.by(articlePaginationDto.getSortBy());

            if ("dsc".equals(articlePaginationDto.getSortDirection())) {
                sort = sort.descending();
            } else {
                sort = sort.ascending();
            }
        } else {
            sort = Sort.by("publishDate").descending();
        }

        Pageable pageable = PageRequest.of(articlePaginationDto.getPageNumber(), articlePaginationDto.getPageSize(), sort);
        Page<Article> articles = this.articleRepository.findAllPublished(pageable);
        List<ArticleDetailsDto> articleList = new ArrayList<>();
        for (Article article : articles) {
            ArticleDetailsDto articleDetailsDto = new ArticleDetailsDto();
            articleDetailsDto.setArticleToken(article.getArticleToken());
            articleDetailsDto.setTitle(article.getTitle());
            articleDetailsDto.setContent(article.getContent());
            articleDetailsDto.setPublishStatus(article.getPublishStatus());
            articleDetailsDto.setPublishDate(article.getPublishDate());
            String departmentName = this.articleRepository.findDepartmentNameByToken(article.getArticleToken());
            articleDetailsDto.setDepartmentName(departmentName);
            List<ArticleMediaDetailsDto> articleMedia = new ArrayList<>();
            Set<ArticleMedia> articleMediaSet = article.getArticleMedia();
            for (ArticleMedia articleMedia1 : articleMediaSet) {
                ArticleMediaDetailsDto articleMediaDetailsDto = new ArticleMediaDetailsDto();
                articleMediaDetailsDto.setUrl(articleMedia1.getUrl());
                articleMediaDetailsDto.setFileName(articleMedia1.getFileName());
                articleMediaDetailsDto.setFormat(articleMedia1.getFormat());
                articleMediaDetailsDto.setMediaType(articleMedia1.getMediaType());
                articleMedia.add(articleMediaDetailsDto);
            }
            articleDetailsDto.setArticleMedia(articleMedia);
            articleList.add(articleDetailsDto);
        }
        return articleList;
    }

    @Override
    public List<ArticleListDto> getArticlesListByDepartment (ArticleFilter articleFilter, Sort sort) {
        Specification<Article> articleSpecification = ArticleSpecification.filterArticles(articleFilter);
        List<Article> articles = this.articleRepository.findAll(articleSpecification, sort);
        List<ArticleListDto> articleList = new ArrayList<>();
        for (Article article : articles) {
            ArticleListDto articleListDto = new ArticleListDto();
            articleListDto.setArticleToken(article.getArticleToken());
            articleListDto.setTitle(article.getTitle());
            articleListDto.setPublishStatus(article.getPublishStatus());
            articleListDto.setPublishDate(article.getPublishDate());
            articleList.add(articleListDto);
        }
        return articleList;
    }


}
