package com.ayushsingh.cacmp_backend.config.security.service;

import com.ayushsingh.cacmp_backend.models.entities.Consumer;
import com.ayushsingh.cacmp_backend.models.securityModels.entity.SecurityConsumer;
import com.ayushsingh.cacmp_backend.repository.ConsumerRepository;
import com.ayushsingh.cacmp_backend.util.exceptionUtil.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConsumerDetailsService implements UserDetailsService {


    private final ConsumerRepository consumerRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Consumer> user = consumerRepository.findByEmail(username);

        if(user.isEmpty()){
            throw new ApiException("User not found with username: "+username);
        }
        else{
            return new SecurityConsumer(user.get());
        }
    }
}
