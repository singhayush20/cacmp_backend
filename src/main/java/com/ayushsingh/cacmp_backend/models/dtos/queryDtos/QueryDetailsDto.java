package com.ayushsingh.cacmp_backend.models.dtos.queryDtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QueryDetailsDto {

    private String queryToken;

    private String email;

    private String name;

    private Long phone;

    private String message;

    private Date createdAt;

    private Long count;
}
