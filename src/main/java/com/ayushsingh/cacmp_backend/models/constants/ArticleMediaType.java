package com.ayushsingh.cacmp_backend.models.constants;

import com.ayushsingh.cacmp_backend.util.exceptionUtil.ApiException;
import lombok.Getter;

@Getter
public enum ArticleMediaType {
    IMAGE("IMAGE"), VIDEO("VIDEO");
    private final String value;


    ArticleMediaType (String mediaType) {
        this.value = mediaType;
    }

    public static ArticleMediaType fromValue(String value) {
        for (ArticleMediaType mediaType: values()) {
            if (mediaType.getValue().equalsIgnoreCase(value)) {
                return mediaType;
            }
        }
        throw new ApiException("No enum constant with value: " + value);
    }
}
