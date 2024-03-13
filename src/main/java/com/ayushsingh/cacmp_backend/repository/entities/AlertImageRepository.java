package com.ayushsingh.cacmp_backend.repository.entities;

import com.ayushsingh.cacmp_backend.models.entities.AlertImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AlertImageRepository extends JpaRepository<AlertImage, Long> {

    @Query("SELECT a.imageUrl FROM AlertImage a WHERE a.alert.alertToken = ?1")
    List<String> findAllUrlsByAlertId(String token);
}
