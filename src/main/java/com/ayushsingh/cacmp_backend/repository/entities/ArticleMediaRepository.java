package com.ayushsingh.cacmp_backend.repository.entities;

import com.ayushsingh.cacmp_backend.models.entities.ArticleMedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface ArticleMediaRepository extends JpaRepository<ArticleMedia, String> {




    @Modifying
    @Query("DELETE FROM ArticleMedia  a WHERE a.publicId =?1")
    void deleteByPublicId(String publicId);

    @Query("SELECT a FROM ArticleMedia a WHERE a.mediaToken IN ?1")
    List<ArticleMedia> findAllByMediaTokens(Set<String> imageTokens);
}
