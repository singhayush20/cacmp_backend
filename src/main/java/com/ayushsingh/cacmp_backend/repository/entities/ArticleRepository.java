package com.ayushsingh.cacmp_backend.repository.entities;

import com.ayushsingh.cacmp_backend.models.entities.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query("SELECT a FROM Article a WHERE a.articleToken = ?1")
    Optional<Article> findByArticleToken (String articleToken);

    @Query("SELECT a.department.departmentName FROM Article a WHERE a.articleToken = ?1")
    String findDepartmentNameByToken (String articleToken);

    @Query("SELECT a FROM Article a WHERE a.publishStatus = PublishStatus.PUBLISHED")
    Page<Article> findAllPublished (Pageable pageable);

    List<Article> findAll (Specification<Article> articleSpecification, Sort sort);
}
