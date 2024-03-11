package com.ayushsingh.cacmp_backend.services;

import com.ayushsingh.cacmp_backend.models.dtos.complaintFeedbackDtos.ComplaintFeedbackDto;
import com.ayushsingh.cacmp_backend.models.entities.ComplaintFeedback;
import com.ayushsingh.cacmp_backend.models.projections.feedbackComplaint.ComplaintFeedbackProjection;

public interface ComplaintFeedbackService {

    String saveFeedback(ComplaintFeedbackDto complaintFeedback);
    ComplaintFeedbackProjection getFeedbackForComplaint(String complaintToken);
}
