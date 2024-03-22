package com.ayushsingh.cacmp_backend.models.constants;

import lombok.Getter;

import java.util.EnumSet;
import java.util.Set;


@Getter
public enum AlertInputType {

    TEXT("TEXT"), //text and image
    DOCUMENT("DOCUMENT"); //only document

    private final String value;


    AlertInputType (String value) {
        this.value = value;
    }

    private Set<AlertInputType> getAllTypes () {
        return EnumSet.allOf(AlertInputType.class);
    }
}
