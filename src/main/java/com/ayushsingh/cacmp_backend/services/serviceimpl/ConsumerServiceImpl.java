package com.ayushsingh.cacmp_backend.services.serviceimpl;

import com.ayushsingh.cacmp_backend.models.dtos.consumerDtos.ConsumerRegisterDto;
import com.ayushsingh.cacmp_backend.models.entities.Consumer;
import com.ayushsingh.cacmp_backend.models.entities.ConsumerAddress;
import com.ayushsingh.cacmp_backend.models.roles.ConsumerRole;
import com.ayushsingh.cacmp_backend.repository.entities.ConsumerAddressRepository;
import com.ayushsingh.cacmp_backend.repository.entities.ConsumerRepository;
import com.ayushsingh.cacmp_backend.services.ConsumerRoleService;
import com.ayushsingh.cacmp_backend.services.ConsumerService;
import com.ayushsingh.cacmp_backend.util.exceptionUtil.ApiException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;

    @Override
    public Boolean isConsumerPresent(String email) {
        return consumerRepository.existsByEmail(email);
    }

    @Transactional
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
            consumer.setPhone(consumerRegisterDto.getPhone());
            consumer.setGender(consumerRegisterDto.getGender());
            consumer.setName(consumerRegisterDto.getName());
            consumer.setIsEmailVerified(false);
            if(consumerRegisterDto.getAddress()!=null) {
                ConsumerAddress consumerAddress = this.modelMapper.map(consumerRegisterDto.getAddress(), ConsumerAddress.class);
                consumer.setAddress(consumerAddress);
            }
            consumer=consumerRepository.save(consumer);
            return consumer.getConsumerToken();
        }
    }

    @Override
    public String getConsumerToken(String email) {
        return consumerRepository.findTokenByEmail(email);
    }
}
