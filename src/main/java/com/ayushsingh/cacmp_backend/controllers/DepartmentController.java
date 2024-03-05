package com.ayushsingh.cacmp_backend.controllers;

import com.ayushsingh.cacmp_backend.config.security.util.JwtUtil;
import com.ayushsingh.cacmp_backend.constants.AppConstants;
import com.ayushsingh.cacmp_backend.models.dtos.authDtos.RefreshTokenDto;
import com.ayushsingh.cacmp_backend.models.dtos.authDtos.LoginRequestDto;
import com.ayushsingh.cacmp_backend.models.dtos.authDtos.LoginResponseDto;
import com.ayushsingh.cacmp_backend.models.dtos.departmentDtos.DepartmentDetailsDto;
import com.ayushsingh.cacmp_backend.models.dtos.departmentDtos.DepartmentRegisterDto;
import com.ayushsingh.cacmp_backend.models.projections.department.DepartmentNameProjection;
import com.ayushsingh.cacmp_backend.models.securityModels.jwt.RefreshToken;
import com.ayushsingh.cacmp_backend.services.DepartmentService;
import com.ayushsingh.cacmp_backend.services.RefreshTokenService;
import com.ayushsingh.cacmp_backend.util.cookieUtil.CookieUtil;
import com.ayushsingh.cacmp_backend.util.exceptionUtil.ApiException;
import com.ayushsingh.cacmp_backend.util.responseUtil.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/department")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;
    private final RefreshTokenService refreshTokenService;

    @Value("${jwt.accessTokenCookieName}")
    private String accessTokenCookieName;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> register(@RequestBody DepartmentRegisterDto departmentRegisterDto){
        String token=departmentService.registerDepartment(departmentRegisterDto);
        return new ResponseEntity<>(new ApiResponse<>(token), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDto>> login(HttpServletResponse httpServletResponse) {
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String token=departmentService.getDepartmentToken(username);
            String accessToken = JwtUtil.generateToken(username, AppConstants.ENTITY_TYPE_DEPARTMENT);
            CookieUtil.create(httpServletResponse, accessTokenCookieName, accessToken, false, -1, "localhost");
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(username, AppConstants.ENTITY_TYPE_DEPARTMENT);
            LoginResponseDto loginResponseDto = new LoginResponseDto();
            loginResponseDto.setAccessToken(accessToken);
            loginResponseDto.setToken(token);
            loginResponseDto.setUsername(username);
            loginResponseDto.setRefreshToken(refreshToken.getRefreshToken());
            return new ResponseEntity<>(new ApiResponse<>(loginResponseDto), HttpStatus.OK);

        }
        throw new ApiException("User authentication failed!");
    }

    @GetMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(HttpServletResponse response){
        CookieUtil.clear(response,accessTokenCookieName);
        return new ResponseEntity<>(new ApiResponse<>("Logged out successfully"),HttpStatus.OK);
    }

    @PutMapping("/refresh-token")
    public ResponseEntity<ApiResponse<String>> refreshJwtToken(@RequestBody RefreshTokenDto refreshTokenDto, HttpServletResponse httpServletResponse) {
        Boolean isRefreshTokenValid=this.refreshTokenService.verifyRefreshToken(refreshTokenDto.getRefreshToken());
        if(isRefreshTokenValid){
            String token= JwtUtil.generateToken(refreshTokenDto.getUsername(), AppConstants.ENTITY_TYPE_DEPARTMENT);
            CookieUtil.create(httpServletResponse, accessTokenCookieName, token, false, -1, "localhost");
            return new ResponseEntity<>(new ApiResponse<>(token),HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(new ApiResponse<>("The refresh token is expired! Cannot generate a new token! Please re-login"),HttpStatus.OK);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<DepartmentDetailsDto>>> getAllDepartments(){
        List<DepartmentDetailsDto> departments=departmentService.listAllDepartments();
        return new ResponseEntity<>(new ApiResponse<>(departments),HttpStatus.OK);
    }

    @DeleteMapping("/{departmentToken}")
    public ResponseEntity<ApiResponse<String>> deleteDepartment(@PathVariable String departmentToken){
        departmentService.deleteDepartment(departmentToken);
        return new ResponseEntity<>(new ApiResponse<>("Department deleted successfully"),HttpStatus.OK);
    }

    @GetMapping("/{departmentToken}")
    public ResponseEntity<ApiResponse<DepartmentDetailsDto>> getDepartment(@PathVariable String departmentToken){
        DepartmentDetailsDto department=departmentService.getDepartment(departmentToken);
        return new ResponseEntity<>(new ApiResponse<>(department),HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<ApiResponse<String>> updateDepartment(@RequestBody DepartmentDetailsDto departmentDetailsDto){
        String token=departmentService.updateDepartment(departmentDetailsDto);
        return new ResponseEntity<>(new ApiResponse<>(token),HttpStatus.OK);
    }

    @GetMapping("/names")
    public ResponseEntity<ApiResponse<List<DepartmentNameProjection>>> getDepartmentNames(){
        List<DepartmentNameProjection> departments=departmentService.getDepartmentNames();
        return new ResponseEntity<>(new ApiResponse<>(departments),HttpStatus.OK);
    }


}

