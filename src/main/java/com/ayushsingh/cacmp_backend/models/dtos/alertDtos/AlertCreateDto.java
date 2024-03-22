package com.ayushsingh.cacmp_backend.models.dtos.alertDtos;

import com.ayushsingh.cacmp_backend.models.constants.AlertInputType;
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

    private String departmentToken;

    private AlertInputType alertInputType;

}
