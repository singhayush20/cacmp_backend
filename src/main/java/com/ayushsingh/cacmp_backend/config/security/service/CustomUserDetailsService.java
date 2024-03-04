package com.ayushsingh.cacmp_backend.config.security.service;

import com.ayushsingh.cacmp_backend.models.entities.User;
import com.ayushsingh.cacmp_backend.models.securityModels.entity.SecurityUser;
import com.ayushsingh.cacmp_backend.repository.entities.UserRepository;
import com.ayushsingh.cacmp_backend.util.exceptionUtil.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);

        return user.map(SecurityUser::new).orElse(null);
    }
}
