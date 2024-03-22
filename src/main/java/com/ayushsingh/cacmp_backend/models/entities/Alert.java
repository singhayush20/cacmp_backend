package com.ayushsingh.cacmp_backend.models.entities;

import com.ayushsingh.cacmp_backend.models.constants.AlertInputType;
import com.ayushsingh.cacmp_backend.models.constants.PublishStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="alert")
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="alert_id")
    private Long alertId;

    @Column(name="alert_token",nullable = false,unique = true)
    private String alertToken;

    @Column(name="subject",nullable = false,length=300)
    private String subject;

    @Column(name="message",length = 600)
    private String message;

    @Column(name="inputType",nullable = false)
    @Enumerated(value = EnumType.STRING)
    private AlertInputType inputType;

    @OneToMany(mappedBy = "alert", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<AlertImage> alertImages = new HashSet<>();

    @OneToMany(mappedBy = "alert", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<AlertDocument> alertDocuments = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH})
    @JoinColumn(name = "department_id", referencedColumnName = "department_id")
    private Department department;

    @Column(name="status",nullable = false)
    @Enumerated(value = EnumType.STRING)
    private PublishStatus publishStatus;

    @CreatedDate
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @LastModifiedDate
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name="published_on")
    private Date publishedOn;

    @PrePersist
    public void generateToken() {
        if (this.alertToken == null) {
            this.alertToken = UUID.randomUUID().toString();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Alert alert = (Alert) o;
        return Objects.equals(alertId, alert.alertId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(alertId);
    }
}
