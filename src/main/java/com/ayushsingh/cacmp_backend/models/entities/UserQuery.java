package com.ayushsingh.cacmp_backend.models.entities;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="user_query")
@Entity
public class UserQuery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="query_id")
    private Long queryId;

    @Column(name="query_token",nullable = false,unique = true)
    private String queryToken;

    @Column(name="email",nullable = false)
    private String email;

    @Column(name="name",nullable = false)
    private String name;

    @Column(name="phone",nullable = false,length=10)
    private Long phone;

    @Column(name="message",nullable = false,length=500)
    private String message;


    @CreatedDate
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @LastModifiedDate
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    @PrePersist
    public void generateQueryToken(){
        this.queryToken = String.valueOf(System.currentTimeMillis());
    }
}
