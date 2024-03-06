package com.ayushsingh.cacmp_backend.repository.entities;

import com.ayushsingh.cacmp_backend.models.constants.ComplaintStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ayushsingh.cacmp_backend.models.entities.Complaint;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ComplaintRepository extends JpaRepository<Complaint,Long>{

    @Query("UPDATE Complaint c set c.complaintStatus =?1 WHERE c.complaintToken =?2")
    @Modifying
    void updateComplaintStatus(ComplaintStatus newStatus, String complaintToken);
}
