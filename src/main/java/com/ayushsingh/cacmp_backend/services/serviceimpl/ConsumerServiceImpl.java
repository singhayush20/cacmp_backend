package com.ayushsingh.cacmp_backend.services.serviceimpl;

import com.ayushsingh.cacmp_backend.models.dtos.consumerDtos.ConsumerRegisterDto;
import com.ayushsingh.cacmp_backend.models.entities.Consumer;
import com.ayushsingh.cacmp_backend.models.roles.ConsumerRole;
import com.ayushsingh.cacmp_backend.repository.ConsumerRepository;
import com.ayushsingh.cacmp_backend.services.ConsumerRoleService;
import com.ayushsingh.cacmp_backend.services.ConsumerService;
import com.ayushsingh.cacmp_backend.util.exceptionUtil.ApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConsumerServiceImpl implements ConsumerService {

    private final ConsumerRepository consumerRepository;
    private final ConsumerRoleService consumerRoleService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Boolean isConsumerPresent(String email) {
        return consumerRepository.existsByEmail(email);
    }

    @Override
    public String registerConsumer(ConsumerRegisterDto consumerRegisterDto) {
        String email=consumerRegisterDto.getEmail();
        Boolean isConsumerPresent = isConsumerPresent(email);
        if(isConsumerPresent){
            throw new ApiException("Consumer with email: "+email+" already exists");
        }
        else{
            Consumer consumer=new Consumer();
            Set<ConsumerRole> roles=new HashSet<>();
            Set<String> consumerRoles=consumerRegisterDto.getRoles();
            for(String consumerRole: consumerRoles){
                ConsumerRole role=consumerRoleService.getConsumerRoleByRoleName(consumerRole);
                roles.add(role);
            }
            consumer.setEmail(email);
            consumer.setPassword(passwordEncoder.encode(consumerRegisterDto.getPassword()));
            consumer.setRoles(roles);
            consumer=consumerRepository.save(consumer);
            return consumer.getConsumerToken();
        }
    }
}
