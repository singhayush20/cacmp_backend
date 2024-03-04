package com.ayushsingh.cacmp_backend.services.serviceimpl;

import com.ayushsingh.cacmp_backend.models.roles.DepartmentRole;
import com.ayushsingh.cacmp_backend.repository.roles.DepartmentRoleRepository;
import com.ayushsingh.cacmp_backend.services.DepartmentRoleService;
import com.ayushsingh.cacmp_backend.util.exceptionUtil.ApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class DepartmentRoleServiceImpl implements DepartmentRoleService {

    private final DepartmentRoleRepository departmentRoleRepository;
    @Override
    public DepartmentRole getDepartmentRoleByRoleName(String roleName) {
        Optional<DepartmentRole> userRole=departmentRoleRepository.findByRoleName(roleName);
        if(userRole.isPresent()){
            return userRole.get();
        }
        else{
            throw new ApiException("Department role with name: "+roleName+" does not exist");
        }
    }

    @Override
    public Set<String> listDepartmentRoles() {
        return departmentRoleRepository.listDepartmentRoles();
    }
}
