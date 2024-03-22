package com.ayushsingh.cacmp_backend.models.dtos.pollDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PollListDto {

    String pollToken;
    String subject;
    String description;
    Date createdOn;
    Date liveOn;
    Boolean isLive;
}
