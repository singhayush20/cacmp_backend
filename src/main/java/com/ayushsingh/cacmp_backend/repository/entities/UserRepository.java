package com.ayushsingh.cacmp_backend.repository.entities;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ayushsingh.cacmp_backend.models.entities.User;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{


    @Query("SELECT u FROM User u WHERE u.username = ?1")
    public Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    @Query("SELECT u.userToken FROM User u WHERE u.username = ?1")
    String findTokenByUsername(String username);
}
