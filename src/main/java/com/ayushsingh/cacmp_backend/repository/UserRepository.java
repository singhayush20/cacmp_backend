package com.ayushsingh.cacmp_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ayushsingh.cacmp_backend.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
    
}
