package com.ayushsingh.cacmp_backend.config.security.service;

import com.ayushsingh.cacmp_backend.models.entities.Department;
import com.ayushsingh.cacmp_backend.models.securityModels.entity.SecurityDepartment;
import com.ayushsingh.cacmp_backend.repository.entities.DepartmentRepository;
import com.ayushsingh.cacmp_backend.util.exceptionUtil.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DepartmentDetailsService implements UserDetailsService {

private final DepartmentRepository departmentRepository;
@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Department> department = departmentRepository.findByUsername(username);

        if(department.isEmpty()){
            throw new ApiException("Department not found with username: "+username);
        }
        else{
            return new SecurityDepartment(department.get());
        }
    }
}
