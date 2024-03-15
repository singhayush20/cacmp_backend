package com.ayushsingh.cacmp_backend.models.dtos.pollDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PollChoiceDetailsDto {
    private String choiceToken;
    private String choiceName;
    private Long voteCount;
    private Integer sequenceNo;
}
