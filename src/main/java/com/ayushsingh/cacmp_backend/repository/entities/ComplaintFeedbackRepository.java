package com.ayushsingh.cacmp_backend.repository.entities;

import com.ayushsingh.cacmp_backend.models.entities.ComplaintFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ComplaintFeedbackRepository extends JpaRepository<ComplaintFeedback, Long> {



    @Query("SELECT c FROM ComplaintFeedback c WHERE c.complaint.complaintToken = ?1")
    Optional<ComplaintFeedback> findByComplaintId(String complaintToken);
}
