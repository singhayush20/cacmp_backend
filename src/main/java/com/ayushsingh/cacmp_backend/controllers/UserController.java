package com.ayushsingh.cacmp_backend.controllers;

import com.ayushsingh.cacmp_backend.config.security.util.JwtUtil;
import com.ayushsingh.cacmp_backend.constants.AppConstants;
import com.ayushsingh.cacmp_backend.models.dtos.authDtos.RefreshTokenDto;
import com.ayushsingh.cacmp_backend.models.dtos.authDtos.LoginResponseDto;
import com.ayushsingh.cacmp_backend.models.dtos.userDtos.UserDetailsDto;
import com.ayushsingh.cacmp_backend.models.dtos.userDtos.UserPasswordResetDto;
import com.ayushsingh.cacmp_backend.models.dtos.userDtos.UserRegisterDto;
import com.ayushsingh.cacmp_backend.models.securityModels.jwt.RefreshToken;
import com.ayushsingh.cacmp_backend.services.RefreshTokenService;
import com.ayushsingh.cacmp_backend.services.UserService;
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

import java.util.List;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final RefreshTokenService refreshTokenService;
    private final JwtUtil jwtUtil;

    @Value("${jwt.accessTokenCookieName}")
    private String accessTokenCookieName;

    @PreAuthorize("hasRole('ROLE_ROOT_ADMIN')")
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> register(@RequestBody UserRegisterDto userRegisterDto){
        String consumerToken=userService.registerUser(userRegisterDto);
        return new ResponseEntity<>(new ApiResponse<>(consumerToken), HttpStatus.CREATED);
    }


    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDto>> login(HttpServletResponse httpServletResponse) {
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String token=userService.getUserToken(username);
            String accessToken = jwtUtil.generateToken(username, AppConstants.ENTITY_TYPE_USER);
            CookieUtil.create(httpServletResponse, accessTokenCookieName, accessToken, false, -1, "localhost");
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(username, AppConstants.ENTITY_TYPE_USER);
            LoginResponseDto loginResponseDto = new LoginResponseDto();
            loginResponseDto.setAccessToken(accessToken);
            loginResponseDto.setToken(token);
            loginResponseDto.setUsername(username);
            loginResponseDto.setRefreshToken(refreshToken.getRefreshToken());
            return new ResponseEntity<>(new ApiResponse<>(loginResponseDto), HttpStatus.OK);

        }
        throw new ApiException("User authentication failed!");
    }

    @PreAuthorize("hasAnyRole('ROLE_ROOT_ADMIN', 'ROLE_SUB_ADMIN')")
    @GetMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(HttpServletResponse response){
        CookieUtil.clear(response,accessTokenCookieName);
        return new ResponseEntity<>(new ApiResponse<>("Logged out successfully"),HttpStatus.OK);
    }
    @PutMapping("/refresh-token")
    public ResponseEntity<ApiResponse<String>> refreshJwtToken(@RequestBody RefreshTokenDto refreshTokenDto, HttpServletResponse httpServletResponse) {
        Boolean isRefreshTokenValid=this.refreshTokenService.verifyRefreshToken(refreshTokenDto.getRefreshToken());
        if(isRefreshTokenValid){
            String token= jwtUtil.generateToken(refreshTokenDto.getUsername(), AppConstants.ENTITY_TYPE_USER);
            CookieUtil.create(httpServletResponse, accessTokenCookieName, token, false, -1, "localhost");
            return new ResponseEntity<>(new ApiResponse<>(token),HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(new ApiResponse<>("The refresh token is expired! Cannot generate a new token! Please re-login"),HttpStatus.OK);
        }
    }
    @PreAuthorize("hasAnyRole('ROLE_ROOT_ADMIN', 'ROLE_SUB_ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<UserDetailsDto>>> getAllUsers(){
        return new ResponseEntity<>(new ApiResponse<>(userService.listAllUsers()),HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ROOT_ADMIN')")
    @DeleteMapping("/{userToken}")
    public ResponseEntity<ApiResponse<String>> deleteUser(@PathVariable String userToken){
        userService.deleteUser(userToken);
        return new ResponseEntity<>(new ApiResponse<>("User deleted successfully"),HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ROOT_ADMIN', 'ROLE_SUB_ADMIN')")
    @GetMapping("/{userToken}")
    public ResponseEntity<ApiResponse<UserDetailsDto>> getUser(@PathVariable String userToken){
        return new ResponseEntity<>(new ApiResponse<>(userService.getUser(userToken)),HttpStatus.OK);
    }


    @PreAuthorize("hasAnyRole('ROLE_ROOT_ADMIN')")
    @PutMapping("")
    public ResponseEntity<ApiResponse<String>> updateUser(@RequestBody UserDetailsDto userDetailsDto){
        String token=userService.updateUser(userDetailsDto);
        return new ResponseEntity<>(new ApiResponse<>(token),HttpStatus.OK);
    }

    @GetMapping("/password/reset")
    public ResponseEntity<ApiResponse<String>> sendPasswordChangeVerificationOTP(@RequestParam("email") String email) {
        return new ResponseEntity<>(new ApiResponse<>(this.userService.sendPasswordVerificationOTP(email)), HttpStatus.OK);
    }

    @PutMapping("password/change")
    public ResponseEntity<ApiResponse<String>>putMethodName(@RequestBody UserPasswordResetDto userPasswordResetDto) {
        return new ResponseEntity<>(new ApiResponse<>(this.userService.resetPassword(userPasswordResetDto)), HttpStatus.OK);
    }

}
