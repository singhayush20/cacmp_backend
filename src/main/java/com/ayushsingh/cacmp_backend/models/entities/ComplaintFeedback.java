package com.ayushsingh.cacmp_backend.models.entities;

import com.ayushsingh.cacmp_backend.models.constants.FeedbackRating;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
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
import java.util.UUID;

@Entity
@Table(name = "complaint_feedback")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ComplaintFeedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "complaint_feedback_id")
    private Long complaintFeedbackId;

    @Column(name = "feedback_token", nullable = false, unique = true)
    private String feedbackToken;

    @Column(name="feedback_description",length=500)
    private String feedbackDescription;

    @Column(name = "feedback_rating",nullable = false)
    @Enumerated(value = EnumType.STRING)
    private FeedbackRating feedbackRating;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "complaint_id", referencedColumnName = "complaint_id")
    private Complaint complaint;

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
        if (this.feedbackToken == null) {
            this.feedbackToken = UUID.randomUUID().toString();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComplaintFeedback feedback = (ComplaintFeedback) o;
        return Objects.equals(complaintFeedbackId, feedback.complaintFeedbackId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(complaintFeedbackId);
    }
}
