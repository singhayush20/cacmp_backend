package com.ayushsingh.cacmp_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ayushsingh.cacmp_backend.entity.Complaint;

public interface ComplaintRepository extends JpaRepository<Complaint,Long>{
    
}
