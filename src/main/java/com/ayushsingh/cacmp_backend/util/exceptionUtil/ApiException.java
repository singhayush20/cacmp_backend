package com.ayushsingh.cacmp_backend.util.exceptionUtil;
/*
 * ApiException- class representing custom exceptions thrown in the business logic, for sending a response to the client
 */
public class ApiException extends RuntimeException{
    
    ApiException(String message){
        super(message);
    }
}
