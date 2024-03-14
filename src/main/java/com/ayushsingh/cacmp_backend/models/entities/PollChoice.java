package com.ayushsingh.cacmp_backend.models.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PollChoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="choice_id")
    private Long choiceId;

    @Column(name="choice_token",nullable = false,unique = true)
    private String choiceToken;

    @Column(name="choice_name",nullable = false)
    private String choiceName;

    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.DETACH})
    @JoinColumn(name = "poll_id",referencedColumnName = "poll_id")
    private Poll poll;

    @Column(name="vote_count",nullable = false)
    private Long voteCount;

    @PrePersist
    public void generateToken() {
        this.choiceToken = UUID.randomUUID().toString();
        this.voteCount=0L;
    }
}
