package com.ayushsingh.cacmp_backend.services;

import com.ayushsingh.cacmp_backend.models.dtos.departmentDtos.DepartmentDetailsDto;
import com.ayushsingh.cacmp_backend.models.dtos.departmentDtos.DepartmentPasswordChangeDto;
import com.ayushsingh.cacmp_backend.models.dtos.departmentDtos.DepartmentRegisterDto;
import com.ayushsingh.cacmp_backend.models.projections.department.DepartmentNameProjection;

import java.util.List;

public interface DepartmentService {

     Boolean isDepartmentCredentialsPresent(String username);

    String registerDepartment(DepartmentRegisterDto departmentDto);

    String getDepartmentToken(String username);

    List<DepartmentDetailsDto> listAllDepartments();

    void deleteDepartment(String departmentToken);

    DepartmentDetailsDto getDepartment(String departmentToken);

    String updateDepartment(DepartmentDetailsDto departmentDetailsDto);

    List<DepartmentNameProjection> getDepartmentNames();

    String updateDepartmentPassword(DepartmentPasswordChangeDto departmentPasswordChangeDto);
}
