package com.ayushsingh.cacmp_backend.repository.entities;

import com.ayushsingh.cacmp_backend.models.entities.AlertDocument;
import com.ayushsingh.cacmp_backend.models.projections.alertDocument.AlertDocumentUrlProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AlertDocumentRepository extends JpaRepository<AlertDocument, Long> {

    @Query("""
            SELECT d.documentToken as documentToken,
            d.documentUrl as documentUrl,
            d.documentName as documentName,
            d.format as format
            FROM AlertDocument d WHERE d.alert.alertToken = ?1
            """)
    List<AlertDocumentUrlProjection> findAllByAlertToken(String alertToken);

}
