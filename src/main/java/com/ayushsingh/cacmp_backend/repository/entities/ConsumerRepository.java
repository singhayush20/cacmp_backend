package com.ayushsingh.cacmp_backend.repository.entities;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ayushsingh.cacmp_backend.models.entities.Consumer;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ConsumerRepository extends JpaRepository<Consumer, Long> {

    @Query("select c from com.ayushsingh.cacmp_backend.models.entities.Consumer c where c.email = ?1")
    Optional<Consumer> findByEmail(String username);

    Boolean existsByEmail(String email);

    @Query("select c.consumerToken from com.ayushsingh.cacmp_backend.models.entities.Consumer c where c.email = ?1")
    String findTokenByEmail(String email);
}
