package com.ayushsingh.cacmp_backend.controllers;

import com.ayushsingh.cacmp_backend.config.security.util.JwtUtil;
import com.ayushsingh.cacmp_backend.constants.AppConstants;
import com.ayushsingh.cacmp_backend.models.dtos.authDtos.RefreshTokenDto;
import com.ayushsingh.cacmp_backend.models.dtos.authDtos.LoginResponseDto;
import com.ayushsingh.cacmp_backend.models.dtos.consumerDtos.ConsumerDetailsDto;
import com.ayushsingh.cacmp_backend.models.projections.consumer.ConsumerDetailsProjection;
import com.ayushsingh.cacmp_backend.models.securityModels.jwt.RefreshToken;
import com.ayushsingh.cacmp_backend.services.ConsumerService;
import com.ayushsingh.cacmp_backend.services.RefreshTokenService;
import com.ayushsingh.cacmp_backend.util.cookieUtil.CookieUtil;
import com.ayushsingh.cacmp_backend.util.exceptionUtil.ApiException;
import com.ayushsingh.cacmp_backend.util.responseUtil.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/consumer")
@RequiredArgsConstructor
public class ConsumerController {

    private final ConsumerService consumerService;
    private final RefreshTokenService refreshTokenService;

    @Value("${jwt.accessTokenCookieName}")
    private String accessTokenCookieName;


    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> register(@RequestBody ConsumerDetailsDto consumerDetailsDto) {
        String consumerToken = consumerService.registerConsumer(consumerDetailsDto);
        return new ResponseEntity<>(new ApiResponse<>(consumerToken), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDto>> login(HttpServletResponse httpServletResponse) {
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String token=consumerService.getConsumerToken(username);
            String accessToken = JwtUtil.generateToken(username, AppConstants.ENTITY_TYPE_CONSUMER);
            CookieUtil.create(httpServletResponse, accessTokenCookieName, accessToken, false, -1, "localhost");
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(username, AppConstants.ENTITY_TYPE_CONSUMER);
            LoginResponseDto loginResponseDto = new LoginResponseDto();
            loginResponseDto.setAccessToken(accessToken);
            loginResponseDto.setToken(token);
            loginResponseDto.setUsername(username);
            loginResponseDto.setRefreshToken(refreshToken.getRefreshToken());
            return new ResponseEntity<>(new ApiResponse<>(loginResponseDto), HttpStatus.OK);

        }
            throw new ApiException("User authentication failed!");
    }

    @PreAuthorize("hasAnyRole('ROLE_RESIDENT', 'ROLE_NON_RESIDENT')")
    @GetMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(HttpServletResponse response){
        CookieUtil.clear(response,accessTokenCookieName);
        return new ResponseEntity<>(new ApiResponse<>("Logged out successfully"),HttpStatus.OK);
    }

    @PutMapping("/refresh-token")
    public ResponseEntity<ApiResponse<String>> refreshJwtToken(@RequestBody RefreshTokenDto refreshTokenDto, HttpServletResponse httpServletResponse) {
        Boolean isRefreshTokenValid=this.refreshTokenService.verifyRefreshToken(refreshTokenDto.getRefreshToken());
        if(isRefreshTokenValid){
            String token= JwtUtil.generateToken(refreshTokenDto.getUsername(), AppConstants.ENTITY_TYPE_CONSUMER);
            CookieUtil.create(httpServletResponse, accessTokenCookieName, token, false, -1, "localhost");
            return new ResponseEntity<>(new ApiResponse<>(token),HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(new ApiResponse<>("The refresh token is expired! Cannot generate a new token! Please re-login"),HttpStatus.OK);
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_RESIDENT', 'ROLE_NON_RESIDENT')")
    @PutMapping("")
    public ResponseEntity<ApiResponse<String>> updateConsumer(@RequestBody ConsumerDetailsDto consumerDto,@RequestParam("token") String userToken){
        String token=consumerService.updateConsumer(consumerDto, userToken);
        return new ResponseEntity<>(new ApiResponse<>(token),HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_RESIDENT', 'ROLE_NON_RESIDENT')")
    @GetMapping("")
    public ResponseEntity<ApiResponse<ConsumerDetailsProjection>> getConsumer(@RequestParam("token") String token){
        ConsumerDetailsProjection consumerDetails=consumerService.getConsumer(token);
        return new ResponseEntity<>(new ApiResponse<>(consumerDetails),HttpStatus.OK);
    }
}
