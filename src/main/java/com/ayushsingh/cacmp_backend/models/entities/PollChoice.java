package com.ayushsingh.cacmp_backend.models.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="poll_choice")
@Entity
public class PollChoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="choice_id")
    private Long choiceId;

    @Column(name="choice_token",nullable = false,unique = true)
    private String choiceToken;

    @Column(name="choice_name",nullable = false)
    private String choiceName;

    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "poll_id",referencedColumnName = "poll_id")
    private Poll poll;

    @Column(name="sequence_no",nullable = false)
    private Integer sequenceNo;

    @Column(name="vote_count",nullable = false)
    private Long voteCount;

    @PreRemove
    public void removeChoice() {
        this.poll=null;
    }

    @PrePersist
    public void generateToken() {
        this.choiceToken = UUID.randomUUID().toString();
        this.voteCount=0L;
    }

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PollChoice that = (PollChoice) o;
        return Objects.equals(choiceId, that.choiceId) && Objects.equals(choiceName, that.choiceName) && Objects.equals(sequenceNo, that.sequenceNo);
    }

    @Override
    public int hashCode () {
        return Objects.hash(choiceId, choiceName, sequenceNo);
    }
}
