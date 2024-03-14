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

    @Query("SELECT p FROM Poll p WHERE p.pollToken = :?1")
    Optional<Poll> findByToken(@Param("token") String token);

    @Query("SELECT p.isLive FROM Poll p WHERE p.pollToken = :?1")
    Boolean findIfPollActive(String pollToken);


    @Query("""
            SELECT p.pollToken as pollToken,
            p.subject as subject,
            p.description as description,
            p.department.departmentToken as deptToken,
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
    @Query("UPDATE Poll p SET p.isLive = :?1 WHERE p.pollToken = :?2")
    void changeLiveStatus(String pollToken, Boolean isLive);


}
