package com.ayushsingh.cacmp_backend.models.entities;

import com.ayushsingh.cacmp_backend.models.constants.PublishStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Long articleId;

    @Column(name = "article_token", nullable = false)
    private String articleToken;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false, length = 5000)
    private String content;

    @Column(name = "slug", nullable = false,unique = true)
    private String slug;

    @Column(name = "description",length = 1000,nullable = false)
    private String description;

    @Column(name = "status", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private PublishStatus publishStatus;

    @OneToMany(mappedBy = "article", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.DETACH})
    private Set<ArticleMedia> articleMedia = new HashSet<>();

    @Column(name="publish_date")
    private Date publishDate;

    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.MERGE,CascadeType.PERSIST,
            CascadeType.REFRESH})
    @JoinColumn(name = "department_id",referencedColumnName = "department_id")
    private Department department;

    @CreatedDate
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @LastModifiedDate
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    @PrePersist
    protected void setToken () {
        articleToken = UUID.randomUUID().toString();
    }


    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return Objects.equals(articleToken, article.articleToken);
    }

    @Override
    public int hashCode () {
        return Objects.hash(articleToken);
    }
}
