package com.ayushsingh.cacmp_backend.util.responseUtil;

import com.ayushsingh.cacmp_backend.constants.AppConstants;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse<T> {
    private Integer code;
    private String message;
    private T data;

    public ApiResponse(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ApiResponse(T data) {
        this.data = data;
        this.code = AppConstants.SUCCESS_CODE;
        this.message = AppConstants.SUCCESS_MESSAGE;
    }
}
