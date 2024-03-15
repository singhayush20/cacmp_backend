package com.ayushsingh.cacmp_backend.models.entities;

import com.ayushsingh.cacmp_backend.models.constants.ArticleMediaType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="article_media")
@Entity
public class ArticleMedia {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="article_media_id")
    private Long mediaId;

    @Column(name="media_token",nullable = false)
    private String mediaToken;

    @Column(name="media_type")
    @Enumerated(EnumType.STRING)
    private ArticleMediaType mediaType;

    @Column(name="format",nullable = false)
    private String format;

    @Column(name="file_name",nullable = false)
    private String fileName;

    @Column(name="url",nullable = false)
    private String url;


    @ManyToOne(fetch=FetchType.LAZY, cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name="article_id",referencedColumnName = "article_id")
    private Article article;

    @CreatedDate
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @LastModifiedDate
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    @PrePersist
    protected void setToken() {
        mediaToken = UUID.randomUUID().toString();
    }

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArticleMedia that = (ArticleMedia) o;
        return Objects.equals(mediaToken, that.mediaToken);
    }

    @Override
    public int hashCode () {
        return Objects.hash(mediaToken);
    }
}
