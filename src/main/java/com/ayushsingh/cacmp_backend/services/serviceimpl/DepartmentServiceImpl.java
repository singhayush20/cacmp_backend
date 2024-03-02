package com.ayushsingh.cacmp_backend.services.serviceimpl;

import com.ayushsingh.cacmp_backend.models.dtos.departmentDtos.DepartmentRegisterDto;
import com.ayushsingh.cacmp_backend.models.entities.Department;
import com.ayushsingh.cacmp_backend.models.roles.DepartmentRole;
import com.ayushsingh.cacmp_backend.repository.entities.DepartmentRepository;
import com.ayushsingh.cacmp_backend.services.DepartmentRoleService;
import com.ayushsingh.cacmp_backend.services.DepartmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final PasswordEncoder passwordEncoder;
    private final DepartmentRoleService departmentRoleService;
    @Override
    public Boolean isDepartmentCredentialsPresent(String username) {
        return departmentRepository.existsByUsername(username);
    }

    @Override
    public String registerDepartment(DepartmentRegisterDto departmentDto) {
        String username=departmentDto.getUsername();
        Boolean isDepartmentCredentialsPresent = isDepartmentCredentialsPresent(username);
        if(isDepartmentCredentialsPresent){
            throw new RuntimeException("Department with username: "+username+" already exists");
        }
        else{
            Department department=new Department();
            department.setUsername(username);
            department.setPassword(passwordEncoder.encode(departmentDto.getPassword()));
            department.setDepartmentName(departmentDto.getDepartmentName());
            department.setDepartmentObjective(departmentDto.getDepartmentObjective());
            department=departmentRepository.save(department);
            Set<DepartmentRole> roles=new HashSet<>();
            for(String role:departmentDto.getRoles()){
                roles.add(departmentRoleService.getDepartmentRoleByRoleName(role));
            }
            department.setRoles(roles);
            departmentRepository.save(department);
            return department.getDeptToken();
        }
    }
}
