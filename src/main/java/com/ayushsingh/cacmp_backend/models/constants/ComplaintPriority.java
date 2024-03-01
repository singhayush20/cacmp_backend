package com.ayushsingh.cacmp_backend.models.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum ComplaintPriority {

    LOW("low"), MEDIUM("medium"), HIGH("high");

    private String value;
}
