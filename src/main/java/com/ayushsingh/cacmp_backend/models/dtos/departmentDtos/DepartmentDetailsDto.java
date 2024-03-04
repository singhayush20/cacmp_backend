package com.ayushsingh.cacmp_backend.models.dtos.departmentDtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDetailsDto {

    private String deptToken;

    private String departmentName;

    private String departmentObjective;

    private String username;
}
