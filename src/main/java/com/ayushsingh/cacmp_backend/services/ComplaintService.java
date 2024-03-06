package com.ayushsingh.cacmp_backend.services;

import com.ayushsingh.cacmp_backend.models.dtos.complaintDtos.ComplaintCreateDto;
import com.ayushsingh.cacmp_backend.models.dtos.complaintDtos.ComplaintStatusDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

public interface ComplaintService {

    String createNewComplaint(ComplaintCreateDto complaintCreateDto, MultipartFile[] images);

    String changeStatus(ComplaintStatusDto complaintDto);
}
