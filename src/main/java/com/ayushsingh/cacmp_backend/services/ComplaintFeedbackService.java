package com.ayushsingh.cacmp_backend.services;

import com.ayushsingh.cacmp_backend.models.dtos.complaintFeedbackDtos.ComplaintFeedbackDto;

public interface ComplaintFeedbackService {

    String saveFeedback(ComplaintFeedbackDto complaintFeedback);
    ComplaintFeedbackDto getFeedbackForComplaint(String complaintToken);
}
