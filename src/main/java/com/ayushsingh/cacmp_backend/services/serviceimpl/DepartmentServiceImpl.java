package com.ayushsingh.cacmp_backend.services.serviceimpl;

import com.ayushsingh.cacmp_backend.models.dtos.departmentDtos.DepartmentDetailsDto;
import com.ayushsingh.cacmp_backend.models.dtos.departmentDtos.DepartmentRegisterDto;
import com.ayushsingh.cacmp_backend.models.entities.Department;
import com.ayushsingh.cacmp_backend.models.projections.department.DepartmentNameProjection;
import com.ayushsingh.cacmp_backend.models.roles.DepartmentRole;
import com.ayushsingh.cacmp_backend.repository.entities.CategoryRepository;
import com.ayushsingh.cacmp_backend.repository.entities.ComplaintRepository;
import com.ayushsingh.cacmp_backend.repository.entities.DepartmentRepository;
import com.ayushsingh.cacmp_backend.repository.entities.PollRepository;
import com.ayushsingh.cacmp_backend.services.DepartmentRoleService;
import com.ayushsingh.cacmp_backend.services.DepartmentService;
import com.ayushsingh.cacmp_backend.util.exceptionUtil.ApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final PollRepository pollRepository;

    private final DepartmentRepository departmentRepository;
    private final PasswordEncoder passwordEncoder;
    private final DepartmentRoleService departmentRoleService;
   private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
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

    @Override
    public String getDepartmentToken(String username) {

        return departmentRepository.findTokenByUsername(username);
    }

    @Override
    public List<DepartmentDetailsDto> listAllDepartments() {
        List<Department> departments=departmentRepository.findAll();
        List<DepartmentDetailsDto> departmentDetailsDtos=departments.stream().map(department -> {
            return this.modelMapper.map(department,DepartmentDetailsDto.class);
        }).toList();
        return departmentDetailsDtos;
    }

    @Transactional
    @Override
    public void deleteDepartment(String departmentToken) {
        Long count=categoryRepository.getCategoryCountByDepartmentUsername(departmentToken);
        Long countPolls=pollRepository.countByDepartmentToken(departmentToken);
        if(count!=0){
            throw new ApiException("Department cannot be deleted because there are categories associated with it");
        }
        departmentRepository.deleteByDeptToken(departmentToken);
    }

    @Override
    public DepartmentDetailsDto getDepartment(String departmentToken) {
        Optional<Department> departmentOptional = departmentRepository.findByDeptToken(departmentToken);
        if(departmentOptional.isEmpty()){
            throw new ApiException("Department with department token: "+departmentToken+" does not exist");
        }
        else{
            return this.modelMapper.map(departmentOptional.get(),DepartmentDetailsDto.class);
        }
    }

    @Override
    public String updateDepartment(DepartmentDetailsDto departmentDetailsDto) {
        String token=departmentDetailsDto.getDeptToken();
        Optional<Department> departmentOptional = departmentRepository.findByDeptToken(token);
        if(departmentOptional.isEmpty()){
            throw new ApiException("Department with department token: "+token+" does not exist");
        }
        else{
            Department department=departmentOptional.get();
            department.setDepartmentName(departmentDetailsDto.getDepartmentName());
            department.setDepartmentObjective(departmentDetailsDto.getDepartmentObjective());
            department=departmentRepository.save(department);
            return department.getDeptToken();
        }
    }

    @Override
    public List<DepartmentNameProjection> getDepartmentNames() {
        return departmentRepository.findALlDepartmentNames();
    }


}
