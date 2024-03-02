package com.ayushsingh.cacmp_backend.models.entities;

import java.util.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name="category")
public class Category {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="category_id")
    private Long categoryId;

    @Column(name="token",nullable = false, unique = true)
    private String categoryToken;

    @Column(name="category_name",nullable = false, unique = true)
    private String categoryName;


    @Column(name="category_description",nullable = false,length = 5000)
    private String categoryDescription;


    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.DETACH})
    @JoinColumn(name = "department_id",referencedColumnName = "department_id")
    private Department department;

    @OneToMany(mappedBy = "category",fetch = FetchType.LAZY,cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private Set<Complaint> complaints = new HashSet<Complaint>();


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
        if (this.categoryToken == null) {
            this.categoryToken = UUID.randomUUID().toString();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(categoryId, category.categoryId) && Objects.equals(categoryToken, category.categoryToken) && Objects.equals(categoryName, category.categoryName) && Objects.equals(categoryDescription, category.categoryDescription) && Objects.equals(department, category.department) && Objects.equals(complaints, category.complaints) && Objects.equals(createdAt, category.createdAt) && Objects.equals(updatedAt, category.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryName);
    }
}
