package com.ayushsingh.cacmp_backend.controllers;

import com.ayushsingh.cacmp_backend.models.dtos.complaintDtos.ComplaintCreateDto;
import com.ayushsingh.cacmp_backend.services.ComplaintService;
import com.ayushsingh.cacmp_backend.util.imageUtil.ImageService;
import com.ayushsingh.cacmp_backend.util.responseUtil.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;


@RestController
@RequestMapping("/complaints")
@RequiredArgsConstructor
public class ComplaintsController {

    private final ComplaintService complaintService;

    @PostMapping("/save")
    public ResponseEntity<ApiResponse<String>> save(@RequestPart("complaintData") ComplaintCreateDto complaintData,
                                       @RequestPart("images") MultipartFile[] images){
        String complaintToken=complaintService.createNewComplaint(complaintData, images);
        return  new ResponseEntity<>(new ApiResponse<>(complaintToken), HttpStatus.CREATED);

    }
}
