package com.ayushsingh.cacmp_backend.controllers;


import com.ayushsingh.cacmp_backend.services.AnalyticsService;
import com.ayushsingh.cacmp_backend.util.responseUtil.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/analytics")
public class AnalyticsController {

    private final AnalyticsService analyticsService;


    @GetMapping(value = "/admin-dashboard",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<Map<String,Object>>> getAdminDashboardAnalytics(){
        Map<String,Object> adminDashboardAnalytics=analyticsService.getAnalytics();
        return new ResponseEntity<>(new ApiResponse<>(adminDashboardAnalytics), HttpStatus.OK);
    }
}
