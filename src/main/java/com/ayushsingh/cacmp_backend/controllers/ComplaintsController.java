package com.ayushsingh.cacmp_backend.controllers;

import com.ayushsingh.cacmp_backend.models.constants.ComplaintPriority;
import com.ayushsingh.cacmp_backend.models.constants.ComplaintStatus;
import com.ayushsingh.cacmp_backend.models.dtos.complaintDtos.ComplaintCreateDto;
import com.ayushsingh.cacmp_backend.models.dtos.complaintDtos.ComplaintListDetailsDto;
import com.ayushsingh.cacmp_backend.models.dtos.complaintDtos.ComplaintStatusDto;
import com.ayushsingh.cacmp_backend.models.projections.complaint.ComplaintDetailsProjection;
import com.ayushsingh.cacmp_backend.models.projections.complaint.ComplaintListDetailsProjection;
import com.ayushsingh.cacmp_backend.repository.filterDto.ComplaintFilter;
import com.ayushsingh.cacmp_backend.services.ComplaintService;
import com.ayushsingh.cacmp_backend.util.responseUtil.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/api/v1/complaints")
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

    @GetMapping("/search/pincode")
    public ResponseEntity<ApiResponse<List<ComplaintListDetailsProjection>>> searchByPincode(@RequestParam("pincode") Long pincode){
        List<ComplaintListDetailsProjection> complaints=complaintService.searchByPincode(pincode);
        return new ResponseEntity<>(new ApiResponse<>(complaints),HttpStatus.OK);
    }

    @GetMapping("/search/ward-no")
    public ResponseEntity<ApiResponse<List<ComplaintListDetailsProjection>>> searchByWardNo(@RequestParam("wardNo") String wardNo){
        List<ComplaintListDetailsProjection> complaints=complaintService.searchByWardNo(wardNo);
        return new ResponseEntity<>(new ApiResponse<>(complaints),HttpStatus.OK);
    }

    @GetMapping("/search/status")
    public  ResponseEntity<ApiResponse<List<ComplaintListDetailsProjection>>> getAllComplaintsByStatus(@RequestParam("status")String status){
        List<ComplaintListDetailsProjection> complaints=complaintService.getAllComplaintsByStatus(status);
        return new ResponseEntity<>(new ApiResponse<>(complaints),HttpStatus.OK);
    }



    @GetMapping("/search/priority")
    public  ResponseEntity<ApiResponse<List<ComplaintListDetailsProjection>>> getAllComplaintsByPriority(@RequestParam("priority")String priority){
        List<ComplaintListDetailsProjection> complaints=complaintService.getAllComplaintsByPriority(priority);
        return new ResponseEntity<>(new ApiResponse<>(complaints),HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<ComplaintListDetailsProjection>>> getAllComplaints(){
        List<ComplaintListDetailsProjection> list=complaintService.getAllComplaints();
        return  new ResponseEntity<>(new ApiResponse<>(list),HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<ApiResponse<List<ComplaintListDetailsDto>>> getFilteredComplaints(
            @RequestParam(required = false) ComplaintStatus status,
            @RequestParam(required = false) ComplaintPriority priority,
            @RequestParam(required = false) Long pincode,
            @RequestParam(required = false) String wardNo,
            @RequestParam(required = false) String sortBy) {

        ComplaintFilter filter = new ComplaintFilter();
        filter.setStatus(status);
        filter.setPriority(priority);
        filter.setPincode(pincode);
        filter.setWardNo(wardNo);

        Sort sort = sortBy != null ? Sort.by(sortBy) : Sort.by("createdAt").descending(); // Default sorting by createdAt

        List<ComplaintListDetailsDto> complaints = complaintService.getFilteredComplaints(filter, sort);
        return new ResponseEntity<>(new ApiResponse<>(complaints), HttpStatus.OK);
    }

    @GetMapping("/details")
    public ResponseEntity<ApiResponse<ComplaintDetailsProjection>> getComplaintDetails(@RequestParam("token") String token){
        ComplaintDetailsProjection complaint=complaintService.getComplaintDetails(token);
        return new ResponseEntity<>(new ApiResponse<>(complaint),HttpStatus.OK);
    }

    @GetMapping("/details/images")
    public ResponseEntity<ApiResponse<List<String>>> getComplaintImages(@RequestParam("token") String token){
        List<String> images=complaintService.getComplaintImages(token);
        return new ResponseEntity<>(new ApiResponse<>(images),HttpStatus.OK);
    }
}
