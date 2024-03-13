package com.ayushsingh.cacmp_backend.repository.entities;

import com.ayushsingh.cacmp_backend.models.constants.PublishStatus;
import com.ayushsingh.cacmp_backend.models.entities.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AlertRepository extends JpaRepository<Alert, Long> {
    @Query("SELECT a FROM Alert a WHERE a.alertToken = ?1")
    Optional<Alert> findByAlertToken(String token);

    @Modifying
    @Query("UPDATE Alert a SET a.publishStatus = ?2 WHERE a.alertToken = ?1")
    void updateStatus(String alertToken, PublishStatus publishStatus);
}
