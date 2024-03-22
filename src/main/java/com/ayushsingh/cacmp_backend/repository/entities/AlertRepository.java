package com.ayushsingh.cacmp_backend.repository.entities;

import com.ayushsingh.cacmp_backend.models.constants.PublishStatus;
import com.ayushsingh.cacmp_backend.models.entities.Alert;
import com.ayushsingh.cacmp_backend.models.entities.Complaint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AlertRepository extends JpaRepository<Alert, Long>, JpaSpecificationExecutor<Alert> {
    @Query("SELECT a FROM Alert a WHERE a.alertToken = ?1")
    Optional<Alert> findByAlertToken(String token);

    @Modifying
    @Query("UPDATE Alert a SET a.publishStatus = ?2 WHERE a.alertToken = ?1")
    void updateStatus(String alertToken, PublishStatus publishStatus);



    @Query("SELECT a FROM Alert a WHERE a.department.deptToken = ?1")
    List<Alert> getAllByDeptToken(String departmentToken);

    @Query("SELECT a FROM Alert a WHERE a.publishStatus = ?1")
    Page<Alert> findAllByPublishStatus (PublishStatus publishStatus, Pageable pageable);
}
