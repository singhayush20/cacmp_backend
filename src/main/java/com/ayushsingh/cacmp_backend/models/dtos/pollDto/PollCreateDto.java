package com.ayushsingh.cacmp_backend.models.dtos.pollDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PollCreateDto {

    private String subject;
    private String description;
    private String deptToken;
    private List<String> pollChoices=new ArrayList<>();
}
