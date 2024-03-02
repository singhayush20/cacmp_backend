package com.ayushsingh.cacmp_backend.repository.entities;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ayushsingh.cacmp_backend.models.entities.Complaint;

public interface ComplaintRepository extends JpaRepository<Complaint,Long>{
    
}
