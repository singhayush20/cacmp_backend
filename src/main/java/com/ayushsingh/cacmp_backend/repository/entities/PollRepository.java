package com.ayushsingh.cacmp_backend.repository.entities;

import com.ayushsingh.cacmp_backend.models.entities.Poll;
import com.ayushsingh.cacmp_backend.models.projections.poll.PollListProjection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PollRepository extends JpaRepository<Poll, Long>, JpaSpecificationExecutor<Poll>{

    @Query("SELECT p FROM Poll p WHERE p.pollToken = :token")
    Optional<Poll> findByToken(@Param("token") String token);

    @Query("SELECT p.isLive FROM Poll p WHERE p.pollToken = :pollToken")
    Boolean findIfPollActive(@Param("pollToken") String pollToken);


    @Query("""
            SELECT p.pollToken as pollToken,
            p.subject as subject,
            p.description as description,
            p.department.deptToken as deptToken,
            p.department.departmentName as departmentName
            FROM Poll p
            WHERE p.isLive = true
            ORDER BY p.createdAt DESC
            """)
    List<PollListProjection> getPollList();

    @Query("""
            SELECT
            p.department.departmentName as departmentName,
            p.department.deptToken as deptToken
            FROM Poll p
            WHERE p.pollToken = :pollToken
            """)
    Map<String, String> getDeptNameAndToken(@Param("pollToken") String pollToken);

    @Modifying
    @Query("UPDATE Poll p SET p.isLive = :isLive WHERE p.pollToken = :pollToken")
    void changeLiveStatus(@Param("pollToken") String pollToken, @Param("isLive") Boolean isLive);



}
