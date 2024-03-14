package com.ayushsingh.cacmp_backend.repository.entities;

import com.ayushsingh.cacmp_backend.models.entities.PollChoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PollChoiceRepository extends JpaRepository<PollChoice, Long> {
    @Modifying
    @Query("UPDATE PollChoice p SET p.voteCount = p.voteCount+1 WHERE p.choiceToken = ?1")
    Boolean addVote(String pollToken);
}
