package com.ayushsingh.cacmp_backend.models.constants;

import com.ayushsingh.cacmp_backend.util.exceptionUtil.ApiException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum ComplaintStatus {

    OPEN("OPEN"), REVIEWED("REVIEWED"), CLOSED("CLOSED");

    private String value;

    public static ComplaintStatus fromValue(String value) {
        for (ComplaintStatus status: values()) {
            if (status.getValue().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new ApiException("No enum constant with value: " + value);
    }
}
