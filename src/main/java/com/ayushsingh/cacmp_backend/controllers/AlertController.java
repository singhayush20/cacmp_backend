package com.ayushsingh.cacmp_backend.controllers;

import com.ayushsingh.cacmp_backend.models.constants.PublishStatus;
import com.ayushsingh.cacmp_backend.models.dtos.alertDtos.*;
import com.ayushsingh.cacmp_backend.repository.filterDto.AlertFilter;
import com.ayushsingh.cacmp_backend.repository.paginationDto.PaginationDto;
import com.ayushsingh.cacmp_backend.services.AlertService;
import com.ayushsingh.cacmp_backend.util.responseUtil.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/alert")
@RequiredArgsConstructor
public class AlertController {

    private final AlertService alertService;


    @PreAuthorize("hasRole('ROLE_DEPARTMENT')")
    @PostMapping("/new")
    public ResponseEntity<ApiResponse<String>> createAlert (@RequestBody AlertCreateDto alertCreateDto) {
        String token = alertService.createAlert(alertCreateDto);
        return new ResponseEntity<>(new ApiResponse<>(token), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ROLE_DEPARTMENT','ROLE_RESIDENT','ROLE_NON_RESIDENT')")
    @GetMapping("")
    public ResponseEntity<ApiResponse<AlertDetailsDto>> getAlertDetails (@RequestParam("token") String token) {
        AlertDetailsDto alertDetailsDto = this.alertService.getAlertDetails(token);
        return new ResponseEntity<>(new ApiResponse<>(alertDetailsDto), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_DEPARTMENT')")
    @PostMapping("/upload/image")
    public ResponseEntity<ApiResponse<String>> saveAlertImages (@RequestParam("token") String token, @RequestPart("images") MultipartFile[] images) {
        return new ResponseEntity<>(new ApiResponse<>(this.alertService.saveAlertImages(token, images)), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_DEPARTMENT')")
    @PostMapping("/upload/file")
    public ResponseEntity<ApiResponse<String>> saveAlertFiles (@RequestParam("token") String token, @RequestPart("file") MultipartFile[] files) {
        String alertToken = alertService.uploadFiles(token, files);
        return new ResponseEntity<>(new ApiResponse<>(alertToken), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_DEPARTMENT')")
    @PutMapping("/update-status")
    public ResponseEntity<ApiResponse<String>> updateStatus (@RequestBody StatusUpdateDto statusUpdateDto) {
        return new ResponseEntity<>(new ApiResponse<>(this.alertService.updateStatus(statusUpdateDto)), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<AlertDeptListDto>>> listAlertsByDepartment (
            @RequestParam("token") String token,
            @RequestParam(value = "sortBy",required = false) String sortBy,
    @RequestParam(value = "status",required = false) PublishStatus status) {

       AlertFilter filter = new AlertFilter();
        filter.setPublishStatus(status);
        Sort sort = sortBy != null ? Sort.by(sortBy) : Sort.by("createdAt");
        sort = sort.descending();
        List<AlertDeptListDto> alerts = this.alertService.listAlertsByDepartment( filter, sort);
        return new ResponseEntity<>(new ApiResponse<>(alerts), HttpStatus.OK);
    }

    @GetMapping("/feed")
    public ResponseEntity<ApiResponse<List<AlertFeedDto>>> getAlertFeed(@RequestBody PaginationDto pageDto) {
        List<AlertFeedDto> alerts = this.alertService.getAlertFeed(pageDto);
        return new ResponseEntity<>(new ApiResponse<>(alerts), HttpStatus.OK);
    }
}
