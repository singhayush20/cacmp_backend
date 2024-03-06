package com.ayushsingh.cacmp_backend.services;

import com.ayushsingh.cacmp_backend.models.dtos.complaintDtos.ComplaintCreateDto;
import com.ayushsingh.cacmp_backend.models.dtos.complaintDtos.ComplaintListDetailsDto;
import com.ayushsingh.cacmp_backend.models.dtos.complaintDtos.ComplaintStatusDto;
import com.ayushsingh.cacmp_backend.models.entities.Complaint;
import com.ayushsingh.cacmp_backend.models.projections.complaint.ComplaintDetailsProjection;
import com.ayushsingh.cacmp_backend.models.projections.complaint.ComplaintListDetailsProjection;

import java.util.List;

import com.ayushsingh.cacmp_backend.repository.filterDto.ComplaintFilter;
import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;


public interface ComplaintService {

    String createNewComplaint(ComplaintCreateDto complaintCreateDto, MultipartFile[] images);

    String changeStatus(ComplaintStatusDto complaintDto);

    List<ComplaintListDetailsProjection> searchByPincode(Long pincode);

    List<ComplaintListDetailsProjection> searchByWardNo(String wardNo);

    ComplaintDetailsProjection getComplaintDetails(String complaintToken);

    List<String> getComplaintImages(String token);

    List<ComplaintListDetailsProjection> getAllComplaints();

    List<ComplaintListDetailsProjection> getAllComplaintsByStatus(String status);

    List<ComplaintListDetailsProjection> getAllComplaintsByPriority(String priority);



    List<ComplaintListDetailsDto> getFilteredComplaints(ComplaintFilter complaintFilter, Sort sort);
}
