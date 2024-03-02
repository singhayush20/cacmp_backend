package com.ayushsingh.cacmp_backend.repository.jwt;

import com.ayushsingh.cacmp_backend.models.securityModels.jwt.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByRefreshToken(String refreshToken);

    @Query("SELECT rt FROM RefreshToken rt WHERE "
            + "(rt.tokenType = :type) AND "
            + "CASE "
            + "WHEN rt.tokenType = 'CONSUMER' THEN rt.consumer.consumerId = :id "
            + "WHEN rt.tokenType = 'USER' THEN rt.user.userId = :id "
            + "WHEN rt.tokenType = 'DEPARTMENT' THEN rt.department.departmentId = :id "
            + "ELSE NULL "
            + "END IS NOT NULL")
    Optional<RefreshToken> findByTypeAndId(@Param("type") String type, @Param("id") Long id);

}
