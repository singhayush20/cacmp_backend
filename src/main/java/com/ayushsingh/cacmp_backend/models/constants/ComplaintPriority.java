package com.ayushsingh.cacmp_backend.models.constants;

import com.ayushsingh.cacmp_backend.util.exceptionUtil.ApiException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum ComplaintPriority {

    LOW("low"), MEDIUM("medium"), HIGH("high");

    private String value;

    public static ComplaintPriority fromValue(String value) {
        for (ComplaintPriority priority : values()) {
            if (priority.getValue().equalsIgnoreCase(value)) {
                return priority;
            }
        }
        throw new ApiException("No enum constant with value: " + value);
    }
}
