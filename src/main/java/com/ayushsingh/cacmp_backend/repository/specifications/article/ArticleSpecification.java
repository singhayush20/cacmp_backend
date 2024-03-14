package com.ayushsingh.cacmp_backend.repository.specifications.article;

import com.ayushsingh.cacmp_backend.models.entities.Article;
import com.ayushsingh.cacmp_backend.models.entities.Poll;
import com.ayushsingh.cacmp_backend.repository.filterDto.ArticleFilter;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ArticleSpecification {
    public static Specification<Article> filterArticles(ArticleFilter filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getDeptToken()!= null) {
                predicates.add(criteriaBuilder.equal(root.get("department").get("deptToken"), filter.getDeptToken()));
            }
            if (filter.getPublishStatus() != null) {
                predicates.add(criteriaBuilder.equal(root.get("publishStatus"), filter.getPublishStatus()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
