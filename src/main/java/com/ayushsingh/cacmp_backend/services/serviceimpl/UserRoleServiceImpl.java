package com.ayushsingh.cacmp_backend.services.serviceimpl;

import com.ayushsingh.cacmp_backend.models.roles.UserRole;
import com.ayushsingh.cacmp_backend.repository.UserRoleRepository;
import com.ayushsingh.cacmp_backend.services.UserRoleService;
import com.ayushsingh.cacmp_backend.util.exceptionUtil.ApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository userRoleRepository;
    @Override
    public UserRole getUserRoleByRoleName(String roleName) {
        Optional<UserRole> userRole=userRoleRepository.findByRoleName(roleName);
        if(userRole.isPresent()){
            return userRole.get();
        }
        else{
            throw new ApiException("User role with name: "+roleName+" does not exist");
        }
    }
}
