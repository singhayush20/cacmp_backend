package com.ayushsingh.cacmp_backend.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="alert_documents")
@Entity
public class AlertDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="document_id")
    private Long documentId;

    @Column(name="alert_token",nullable = false,unique = true)
    private String documentToken;

    @Column(name="document_name",nullable = false)
    private String documentName;

    @Column(name="format",nullable = false)
    private String format;

    @Column(name="document_url",nullable = false)
    private String documentUrl;

    @ManyToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="alert_id",referencedColumnName = "alert_id")
    private Alert alert;


    @CreatedDate
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @LastModifiedDate
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlertDocument that = (AlertDocument) o;
        return Objects.equals(documentUrl, that.documentUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(documentUrl);
    }
}
