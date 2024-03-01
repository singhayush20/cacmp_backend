package com.ayushsingh.cacmp_backend.models.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum ComplaintStatus {

    OPEN("Open"), REVIEWED("reviewed"), CLOSED("Closed");

    private String value;
}
