package com.ayushsingh.cacmp_backend.models.dtos.complaintDtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ComplaintCreateDto {

    private String complaintSubject;

    private String complaintDescription;

    private String complaintPriority;

    private String categoryToken;

    private String consumerToken;

    private Long pincode;

    private String wardNo;

    private String address;

}
