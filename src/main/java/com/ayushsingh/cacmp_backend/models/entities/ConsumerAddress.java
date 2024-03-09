package com.ayushsingh.cacmp_backend.models.entities;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity(name = "consumer_address")
public class ConsumerAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long addressId;

    @Column(name = "address_token", nullable = false, unique = true)
    private String addressToken;

    @Column(name = "house_number", nullable = false)
    private String houseNo;

    @Column(name = "locality", nullable = false)
    private String locality;

    @Column(name = "ward_number", nullable = false)
    private String wardNo;

    @Column(name = "pincode", nullable = false, length = 7)
    private Long pinCode;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "state", nullable = false)
    private String state;

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
        if (this.addressToken == null) {
            this.addressToken = UUID.randomUUID().toString();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConsumerAddress that = (ConsumerAddress) o;
        return Objects.equals(houseNo, that.houseNo) && Objects.equals(locality, that.locality) && Objects.equals(wardNo, that.wardNo) && Objects.equals(pinCode, that.pinCode) && Objects.equals(city, that.city) && Objects.equals(state, that.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(houseNo, locality, wardNo, pinCode, city, state);
    }
}
