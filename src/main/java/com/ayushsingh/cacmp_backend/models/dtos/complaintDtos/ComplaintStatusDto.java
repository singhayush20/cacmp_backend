package com.ayushsingh.cacmp_backend.models.dtos.complaintDtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ComplaintStatusDto {

    private String complaintToken;
    private String complaintStatus;
}
