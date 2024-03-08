package com.ayushsingh.cacmp_backend.repository.entities;

import com.ayushsingh.cacmp_backend.models.constants.ComplaintPriority;
import com.ayushsingh.cacmp_backend.models.constants.ComplaintStatus;
import com.ayushsingh.cacmp_backend.models.projections.complaint.ComplaintDetailsProjection;
import com.ayushsingh.cacmp_backend.models.projections.complaint.ComplaintListDetailsProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ayushsingh.cacmp_backend.models.entities.Complaint;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ComplaintRepository extends JpaRepository<Complaint,Long>, JpaSpecificationExecutor<Complaint> {

    @Query("UPDATE Complaint c set c.complaintStatus =?1 WHERE c.complaintToken =?2")
    @Modifying
    void updateComplaintStatus(ComplaintStatus newStatus, String complaintToken);


    @Query("""
            SELECT c.complaintToken as complaintToken,
            c.complaintSubject as complaintSubject,
            c.complaintDescription as complaintDescription,
            c.complaintStatus as complaintStatus,
            c.complaintPriority as complaintPriority
            FROM Complaint c WHERE c.complaintLocation.pincode = ?1
            ORDER BY c.createdAt DESC
            """)
    List<ComplaintListDetailsProjection> findAllByPincode(Long pincode);

    @Query("""
            SELECT c.complaintToken as complaintToken,
            c.complaintSubject as complaintSubject,
            c.complaintDescription as complaintDescription,
            c.complaintStatus as complaintStatus,
            c.complaintPriority as complaintPriority
            FROM Complaint c WHERE c.complaintLocation.wardNo = ?1
            ORDER BY c.createdAt DESC
            """)
    List<ComplaintListDetailsProjection> findAllByWardNo(String wardNo);


    @Query("SELECT c.complaintToken AS complaintToken, " +
            "c.complaintSubject AS complaintSubject, " +
            "c.complaintDescription AS complaintDescription, " +
            "c.complaintStatus AS complaintStatus, " +
            "c.complaintPriority AS complaintPriority, " +
            "cl.pincode AS pincode, " +
            "cl.address AS address, " +
            "cl.wardNo AS wardNo, " +
            "cl.contactNo AS contactNo, " +
            "co.consumerToken AS consumerToken, " +
            "co.name AS consumerName, " +
            "co.phone AS consumerPhone " +
            "FROM Complaint c " +
            "JOIN c.complaintLocation cl " +
            "JOIN c.consumer co " +
            "WHERE c.complaintToken = :complaintToken")
    ComplaintDetailsProjection getComplaintDetails(@Param("complaintToken") String complaintToken);




    @Query("""
            SELECT c.complaintToken as complaintToken,
            c.complaintSubject as complaintSubject,
            c.complaintDescription as complaintDescription,
            c.complaintStatus as complaintStatus,
            c.complaintPriority as complaintPriority
            FROM Complaint c WHERE c.complaintStatus = ?1
            ORDER BY c.createdAt DESC
            """)
    List<ComplaintListDetailsProjection> getAllComplaintsByStatus(ComplaintStatus status);

    @Query("""
            SELECT c.complaintToken as complaintToken,
            c.complaintSubject as complaintSubject,
            c.complaintDescription as complaintDescription,
            c.complaintStatus as complaintStatus,
            c.complaintPriority as complaintPriority
            FROM Complaint c WHERE c.complaintStatus = ?1 AND c.complaintPriority = ?2
            ORDER BY c.createdAt DESC
            """)
    List<ComplaintListDetailsProjection> getAllComplaintsByPriority(ComplaintPriority priority);




    @Query("""
            SELECT c.complaintToken as complaintToken,
            c.complaintSubject as complaintSubject,
            c.complaintDescription as complaintDescription,
            c.complaintStatus as complaintStatus,
            c.complaintPriority as complaintPriority,
            c.category.categoryName as complaintCategory
            FROM Complaint c
            WHERE c.consumer.consumerToken =?1
            ORDER BY c.createdAt DESC
            """)
    List<ComplaintListDetailsProjection> findAllByConsumer(String token);
}
