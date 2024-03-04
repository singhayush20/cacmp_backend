package com.ayushsingh.cacmp_backend.util.exceptionUtil;

public class RefreshTokenExpiredException extends RuntimeException {

    public RefreshTokenExpiredException(String s) {
        super(s);
    }
}
