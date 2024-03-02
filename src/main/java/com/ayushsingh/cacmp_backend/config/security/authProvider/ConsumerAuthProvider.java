package com.ayushsingh.cacmp_backend.config.security.authProvider;

import com.ayushsingh.cacmp_backend.config.security.service.ConsumerDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ConsumerAuthProvider implements AuthenticationProvider {

    private final ConsumerDetailsService consumerDetailsService;
    private final PasswordEncoder passwordEncoder;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = String.valueOf(authentication.getPrincipal());
        String password=String.valueOf(authentication.getCredentials());

        UserDetails consumerDetails = consumerDetailsService.loadUserByUsername(username);
        if(consumerDetails!=null){
            if(passwordEncoder.matches(password,consumerDetails.getPassword())){
                if(consumerDetails.isEnabled()){
                    return new UsernamePasswordAuthenticationToken(username,password,consumerDetails.getAuthorities());
                }
                else{
                    throw new DisabledException("Account has not been verified");
                }
            }
        }
        throw new BadCredentialsException("Wrong Credentials");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }
}
