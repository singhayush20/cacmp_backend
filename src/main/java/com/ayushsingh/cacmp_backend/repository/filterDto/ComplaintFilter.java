package com.ayushsingh.cacmp_backend.repository.filterDto;

import com.ayushsingh.cacmp_backend.models.constants.ComplaintPriority;
import com.ayushsingh.cacmp_backend.models.constants.ComplaintStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComplaintFilter {
    private ComplaintStatus status;
    private ComplaintPriority priority;
    private Long pincode;
    private String wardNo;
    private String categoryToken;
    private String customerToken;
}

