package com.ayushsingh.cacmp_backend.models.dtos.complaintFeedbackDtos;

import com.ayushsingh.cacmp_backend.models.entities.Complaint;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComplaintFeedbackDto {

    private String feedbackDescription;

    private String feedbackRating;

   private String complaintToken;
}
