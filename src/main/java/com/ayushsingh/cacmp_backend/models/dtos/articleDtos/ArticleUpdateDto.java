package com.ayushsingh.cacmp_backend.models.dtos.articleDtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleUpdateDto {
    private String articleToken;
    private String title;
    private String content;
    private String slug;
    private Set<String> imageTokens=new HashSet<>(); //tokens of images which must be removed
    private Set<String> videoTokens=new HashSet<>(); //tokens of videos which must be removed
    private Boolean isMediaTypeChanged; //if true, delete the current files and new files will be saved later
}
