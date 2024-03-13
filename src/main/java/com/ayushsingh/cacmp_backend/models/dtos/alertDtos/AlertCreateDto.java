package com.ayushsingh.cacmp_backend.models.dtos.alertDtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlertCreateDto {

    private String subject;

    private String message;

}
