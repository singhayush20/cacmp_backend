package com.ayushsingh.cacmp_backend.models.dtos.queryDtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QueryCreateDto {

    private String email;

    private String name;

    private Long phone;

    private String message;
}
