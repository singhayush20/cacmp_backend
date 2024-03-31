package com.ayushsingh.cacmp_backend.repository;

import com.ayushsingh.cacmp_backend.models.entities.UserQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserQueryRepository extends JpaRepository<UserQuery, Long> {
    @Modifying
    @Query("DELETE FROM UserQuery u WHERE u.queryToken = ?1")
    void deleteByToken(String queryToken);
}