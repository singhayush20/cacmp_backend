package com.ayushsingh.cacmp_backend.repository.jwt;

import com.ayushsingh.cacmp_backend.models.securityModels.jwt.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByRefreshToken(String refreshToken);

    @Query("SELECT rt FROM RefreshToken rt WHERE "
            + "(TYPE(rt) = :type) AND "
            + "CASE "
            + "WHEN TYPE(rt) = 'ConsumerRefreshToken' THEN rt.consumer.consumerId = :id "
            + "WHEN TYPE(rt) = 'UserRefreshToken' THEN rt.user.userId = :id "
            + "WHEN TYPE(rt) = 'DepartmentRefreshToken' THEN rt.department.departmentId = :id "
            + "ELSE NULL "
            + "END IS NOT NULL")
    Optional<RefreshToken> findByTypeAndId(@Param("type") String type, @Param("id") Long id);
}
