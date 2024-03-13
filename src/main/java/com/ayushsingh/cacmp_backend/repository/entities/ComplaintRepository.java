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

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    @Query("SELECT c FROM Complaint c WHERE c.complaintToken = ?1")
    Optional<Complaint> findByComplaintToken(String complaintToken);


    @Query("SELECT c.complaintStatus AS status, COUNT(c) AS count FROM Complaint c GROUP BY c.complaintStatus")
    List<Map<String, Long>> countByStatus();

    @Query("SELECT c.complaintPriority AS priority, COUNT(c) AS count FROM Complaint c GROUP BY c.complaintPriority")
    List<Map<String, Long>> countByPriority();

    @Query("SELECT c.category.department.departmentName AS department, COUNT(c) AS count FROM Complaint c GROUP BY c.category.department")
    List<Map<String, Long>> countByDepartment();

    @Query("SELECT c.category.categoryName AS category, COUNT(c) AS count FROM Complaint c GROUP BY c.category")
    List<Map<String, Long>> countByCategory();

    @Query("SELECT c.category.department.departmentName AS department, c.complaintStatus AS status, COUNT(c) AS count FROM Complaint c GROUP BY c.category.department, c.complaintStatus")
    List<Map<String, Object>> countByDepartmentAndStatus();

    @Query("SELECT c.category.department.departmentName AS department, c.complaintPriority AS priority, COUNT(c) AS count FROM Complaint c GROUP BY c.category.department, c.complaintPriority")
    List<Map<String, Object>> countByDepartmentAndPriority();

    @Query("SELECT cl.pincode AS pincode, COUNT(c) AS count FROM Complaint c JOIN c.complaintLocation cl GROUP BY cl.pincode")
    List<Map<Long, Long>> countComplaintsByPincode();

    @Query("SELECT cl.wardNo AS wardNo, COUNT(c) AS count FROM Complaint c JOIN c.complaintLocation cl GROUP BY cl.wardNo")
    List<Map<String, Long>> countComplaintsByWardNo();

    @Query("SELECT COUNT(c) FROM Complaint c WHERE c.createdAt BETWEEN :startDate AND :endDate")
    Long countByCreatedAtBetween(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("SELECT COUNT(c) FROM Complaint c WHERE c.closedAt BETWEEN :startDate AND :endDate")
    Long countByClosedAtBetween(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("SELECT c FROM Complaint c WHERE c.closedAt IS NOT NULL")
    List<Complaint> findAllResolvedComplaints();


    @Query("SELECT COUNT(c) FROM Complaint c WHERE c.category.categoryToken = :categoryToken")
    Long countComplaintsByCategoryToken(@Param("categoryToken") String categoryToken);


}
