package com.ayushsingh.cacmp_backend.models.dtos.pollDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PollListDto {

    String pollToken;
    String subject;
    String description;
}
