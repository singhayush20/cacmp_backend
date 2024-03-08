package com.ayushsingh.cacmp_backend.util.exceptionUtil;

public class InsufficientRolesException extends RuntimeException{
    public InsufficientRolesException(String s) {
        super(s);
    }
}
