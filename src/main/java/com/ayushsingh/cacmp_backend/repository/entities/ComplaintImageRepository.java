package com.ayushsingh.cacmp_backend.repository.entities;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ayushsingh.cacmp_backend.models.entities.ComplaintImage;
import com.ayushsingh.cacmp_backend.models.projections.complaint.ComplaintListDetailsProjection;

import java.util.List;

public interface ComplaintImageRepository extends JpaRepository<ComplaintImage, Long> {

    @Query("SELECT ci.imageUrl FROM ComplaintImage ci WHERE ci.complaint.complaintToken = :token")
    List<String> getComplaintImages(String token);

}
