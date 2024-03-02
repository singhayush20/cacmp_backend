package com.ayushsingh.cacmp_backend.services.serviceimpl;

import com.ayushsingh.cacmp_backend.models.roles.ConsumerRole;
import com.ayushsingh.cacmp_backend.repository.roles.ConsumerRoleRepository;
import com.ayushsingh.cacmp_backend.services.ConsumerRoleService;
import com.ayushsingh.cacmp_backend.util.exceptionUtil.ApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ConsumerRoleServiceImpl implements ConsumerRoleService {

    private final ConsumerRoleRepository consumerRoleRepository;
    @Override
    public ConsumerRole getConsumerRoleByRoleName(String roleName) {
        Optional<ConsumerRole> userRole=consumerRoleRepository.findByRoleName(roleName);
        if(userRole.isPresent()){
            return userRole.get();
        }
        else{
            throw new ApiException("Consumer role with name: "+roleName+" does not exist");
        }
    }
}
