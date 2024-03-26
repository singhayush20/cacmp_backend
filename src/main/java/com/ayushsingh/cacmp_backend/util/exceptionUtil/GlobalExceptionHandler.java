package com.ayushsingh.cacmp_backend.util.exceptionUtil;

import com.ayushsingh.cacmp_backend.constants.AppConstants;
import com.ayushsingh.cacmp_backend.util.responseUtil.ApiResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse<String>> handleApiException(ApiException e){
        ApiResponse<String> apiResponse=new ApiResponse<>(AppConstants.ERROR_CODE,AppConstants.ERROR_RESPONSE,e.getMessage());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ApiResponse<String>> handleExpiredJwtException(ExpiredJwtException e){
        ApiResponse<String> apiResponse=new ApiResponse<>(AppConstants.ACCESS_TOKEN_EXPIRED,AppConstants.ERROR_RESPONSE,e.getMessage());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @ExceptionHandler(DuplicateVoteException.class)
    public ResponseEntity<ApiResponse<String>> handleDuplicateVoteException(DuplicateVoteException e){
        ApiResponse<String> apiResponse=new ApiResponse<>(AppConstants.DUPLICATE_VOTE_CODE,AppConstants.DUPLICATE_VOTE_MESSAGE,e.getMessage());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @ExceptionHandler(RefreshTokenExpiredException.class)
    public ResponseEntity<ApiResponse<String>> handleRefreshTokenExpiredException(RefreshTokenExpiredException e){
        ApiResponse<String> apiResponse=new ApiResponse<>(AppConstants.REFRESH_TOKEN_EXPIRED,AppConstants.ERROR_RESPONSE,e.getMessage());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<ApiResponse<String>> handleMalformedJwtException(MalformedJwtException e){
        ApiResponse<String> apiResponse=new ApiResponse<>(AppConstants.ERROR_CODE,AppConstants.ERROR_RESPONSE,e.getMessage());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ApiResponse<String>> handleSignatureException(SignatureException e){
        ApiResponse<String> apiResponse=new ApiResponse<>(AppConstants.ERROR_CODE,AppConstants.ERROR_RESPONSE,e.getMessage());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @ExceptionHandler(UnsupportedJwtException.class)
    public ResponseEntity<ApiResponse<String>> handleUnsupportedJwtException(UnsupportedJwtException e){
        ApiResponse<String> apiResponse=new ApiResponse<>(AppConstants.ERROR_CODE,AppConstants.ERROR_RESPONSE,e.getMessage());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ApiResponse<String>> handleIllegalArgumentException(IllegalArgumentException e){
        e.printStackTrace();
        ApiResponse<String> apiResponse=new ApiResponse<>(AppConstants.ERROR_CODE,AppConstants.ERROR_RESPONSE,e.getMessage());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @ExceptionHandler(InsufficientRolesException.class)
    public ResponseEntity<ApiResponse<String>> handleInsufficientRolesException(InsufficientRolesException e){
        ApiResponse<String> apiResponse=new ApiResponse<>(AppConstants.ACCESS_DENIED_CODE,AppConstants.ACCESS_DENIED_MESSAGE,e.getMessage());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
