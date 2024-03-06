package com.ayushsingh.cacmp_backend.models.entities;

import java.util.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.ayushsingh.cacmp_backend.models.constants.ComplaintPriority;
import com.ayushsingh.cacmp_backend.models.constants.ComplaintStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "complaint")
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "complaint_id")
    private Long complaintId;

    @Column(name = "token", nullable = false, unique = true)
    private String complaintToken;

    @Column(name = "subject", nullable = false, length = 500)
    private String complaintSubject;

    @Column(name = "description", nullable = false, length = 5000)
    private String complaintDescription;

    @Column(name = "status", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private ComplaintStatus complaintStatus;

    @Column(name = "priority", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private ComplaintPriority complaintPriority;

    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private Category category;

    @OneToMany(mappedBy = "complaint", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ComplaintImage> complaintImages = new HashSet<ComplaintImage>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    @JoinColumn(name = "consumer_id", referencedColumnName = "consumer_id", nullable = false)
    private Consumer consumer;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "complaint_location_id", referencedColumnName = "complaint_location_id")
    private ComplaintLocation complaintLocation;

    @CreatedDate
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @LastModifiedDate
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    @PrePersist
    public void generateToken() {
        if (this.complaintToken == null) {
            this.complaintToken = UUID.randomUUID().toString();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Complaint complaint = (Complaint) o;
        return Objects.equals(complaintId, complaint.complaintId)
                && Objects.equals(complaintSubject, complaint.complaintSubject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(complaintId, complaintSubject);
    }
}
