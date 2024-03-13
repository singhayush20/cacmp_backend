package com.ayushsingh.cacmp_backend.models.constants;

import com.ayushsingh.cacmp_backend.util.exceptionUtil.ApiException;
import lombok.Getter;

@Getter
public enum PublishStatus {

     PUBLISHED("PUBLISHED"), DRAFT("DRAFT"), ARCHIVED("ARCHIVED");

    private final String value;
    PublishStatus(String value){
        this.value = value;
    }

    public static PublishStatus fromValue(String value) {
        for (PublishStatus status: values()) {
            if (status.getValue().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new ApiException("No enum constant with value: " + value);
    }
}
