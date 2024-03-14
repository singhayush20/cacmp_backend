package com.ayushsingh.cacmp_backend.repository.entities;

import com.ayushsingh.cacmp_backend.models.entities.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query("SELECT a FROM Article a WHERE a.articleToken = ?1")
    Optional<Article> findByArticleToken (String articleToken);

    @Query("SELECT a.department.departmentName FROM Article a WHERE a.articleToken = ?1")
    String findDepartmentNameByToken (String articleToken);
}
