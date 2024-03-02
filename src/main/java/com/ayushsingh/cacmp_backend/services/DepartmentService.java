package com.ayushsingh.cacmp_backend.services;

import com.ayushsingh.cacmp_backend.models.dtos.departmentDtos.DepartmentRegisterDto;

public interface DepartmentService {

     Boolean isDepartmentCredentialsPresent(String username);

    String registerDepartment(DepartmentRegisterDto departmentDto);
}
