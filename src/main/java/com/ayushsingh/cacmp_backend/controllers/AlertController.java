package com.ayushsingh.cacmp_backend.controllers;

import com.ayushsingh.cacmp_backend.models.dtos.alertDtos.AlertCreateDto;
import com.ayushsingh.cacmp_backend.models.dtos.alertDtos.AlertDetailsDto;
import com.ayushsingh.cacmp_backend.models.dtos.alertDtos.AlertStatusDto;
import com.ayushsingh.cacmp_backend.services.AlertService;
import com.ayushsingh.cacmp_backend.util.responseUtil.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/alert")
@RequiredArgsConstructor
public class AlertController {

    private final AlertService alertService;


    @PostMapping("/new")
    public ResponseEntity<ApiResponse<String>> createAlert(@RequestBody AlertCreateDto alertCreateDto) {
        String token = alertService.createAlert(alertCreateDto);
        return new ResponseEntity<>(new ApiResponse<>(token), HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<AlertDetailsDto>> getAlertDetails(@RequestParam String token) {
        AlertDetailsDto alertDetailsDto = this.alertService.getAlertDetails(token);
        return new ResponseEntity<>(new ApiResponse<>(alertDetailsDto), HttpStatus.OK);
    }

    @PostMapping("/upload/image")
    public ResponseEntity<ApiResponse<String>> saveAlertImages(@RequestParam String token, @RequestParam MultipartFile[] images) {
        return new ResponseEntity<>(new ApiResponse<>(this.alertService.saveAlertImages(token, images)), HttpStatus.CREATED);
    }

    @PostMapping("/upload/file")
    public ResponseEntity<ApiResponse<String>> saveAlertFiles(@RequestParam("alertToken") String token, @RequestPart("file") MultipartFile[] files) {
        String alertToken=alertService.saveAlertImages(token,files);
        return new ResponseEntity<>(new ApiResponse<>(alertToken),HttpStatus.OK);
    }

    @PutMapping("/update-status")
    public ResponseEntity<ApiResponse<String>> updateStatus(@RequestBody AlertStatusDto alertStatusDto) {
        return new ResponseEntity<>(new ApiResponse<>(this.alertService.updateStatus(alertStatusDto)), HttpStatus.OK);
    }


}
