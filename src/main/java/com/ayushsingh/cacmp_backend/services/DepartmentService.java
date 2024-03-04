package com.ayushsingh.cacmp_backend.services;

import com.ayushsingh.cacmp_backend.models.dtos.departmentDtos.DepartmentDetailsDto;
import com.ayushsingh.cacmp_backend.models.dtos.departmentDtos.DepartmentRegisterDto;

import java.util.List;

public interface DepartmentService {

     Boolean isDepartmentCredentialsPresent(String username);

    String registerDepartment(DepartmentRegisterDto departmentDto);

    String getDepartmentToken(String username);

    List<DepartmentDetailsDto> listAllDepartments();

    void deleteDepartment(String departmentToken);

    DepartmentDetailsDto getDepartment(String departmentToken);
}
