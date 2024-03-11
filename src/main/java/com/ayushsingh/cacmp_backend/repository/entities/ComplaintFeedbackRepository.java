package com.ayushsingh.cacmp_backend.repository.entities;

import com.ayushsingh.cacmp_backend.models.entities.ComplaintFeedback;
import com.ayushsingh.cacmp_backend.models.projections.feedbackComplaint.ComplaintFeedbackProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ComplaintFeedbackRepository extends JpaRepository<ComplaintFeedback, Long> {

    @Query("SELECT c.feedbackDescription as comment, c.feedbackRating as rating FROM ComplaintFeedback c WHERE c.complaint.complaintToken = ?1")
    ComplaintFeedbackProjection findByComplaint(String complaintToken);
}
