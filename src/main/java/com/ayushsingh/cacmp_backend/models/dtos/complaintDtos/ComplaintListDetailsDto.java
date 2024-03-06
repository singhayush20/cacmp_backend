package com.ayushsingh.cacmp_backend.models.dtos.complaintDtos;

import com.ayushsingh.cacmp_backend.models.constants.ComplaintPriority;
import com.ayushsingh.cacmp_backend.models.constants.ComplaintStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ComplaintListDetailsDto {

    private String complaintToken;
    private String complaintSubject;
    private String complaintDescription;
    private ComplaintStatus complaintStatus;
    private ComplaintPriority complaintPriority;
}
