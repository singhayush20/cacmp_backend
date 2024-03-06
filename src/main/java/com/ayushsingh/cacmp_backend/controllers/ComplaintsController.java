package com.ayushsingh.cacmp_backend.controllers;

import com.ayushsingh.cacmp_backend.models.dtos.complaintDtos.ComplaintCreateDto;
import com.ayushsingh.cacmp_backend.models.dtos.complaintDtos.ComplaintStatusDto;
import com.ayushsingh.cacmp_backend.services.ComplaintService;
import com.ayushsingh.cacmp_backend.util.responseUtil.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;



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

    @PutMapping("/change-status")
    public ResponseEntity<ApiResponse<String>> changeComplaintStatus(ComplaintStatusDto complaintDto){
        String token=complaintService.changeStatus(complaintDto);
        return new ResponseEntity<>(new ApiResponse<>(token),HttpStatus.OK);
    }
}
