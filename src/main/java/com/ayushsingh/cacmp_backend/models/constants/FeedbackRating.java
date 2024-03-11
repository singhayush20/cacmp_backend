package com.ayushsingh.cacmp_backend.models.constants;

import com.ayushsingh.cacmp_backend.util.exceptionUtil.ApiException;
import lombok.Getter;
import lombok.Setter;

@Getter
public enum FeedbackRating {
    ONE("1"), TWO("2"), THREE("3"), FOUR("4"), FIVE("5");

    private String value;
    FeedbackRating(String value){
        this.value = value;
    }

    public static FeedbackRating fromValue(String value) {
        for (FeedbackRating status: values()) {
            if (status.getValue().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new ApiException("No enum constant with value: " + value);
    }
}
