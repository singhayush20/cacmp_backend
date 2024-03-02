package com.ayushsingh.cacmp_backend.models.dtos.departmentDtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentRegisterDto {

    private String departmentName;

    private String departmentObjective;

    private String password;

    private String username;

    private Set<String> roles;
}
