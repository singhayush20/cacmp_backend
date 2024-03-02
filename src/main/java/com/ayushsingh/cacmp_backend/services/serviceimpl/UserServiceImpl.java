package com.ayushsingh.cacmp_backend.services.serviceimpl;

import com.ayushsingh.cacmp_backend.models.dtos.userDtos.UserRegisterDto;
import com.ayushsingh.cacmp_backend.models.entities.User;
import com.ayushsingh.cacmp_backend.models.roles.UserRole;
import com.ayushsingh.cacmp_backend.repository.entities.UserRepository;
import com.ayushsingh.cacmp_backend.services.UserRoleService;
import com.ayushsingh.cacmp_backend.services.UserService;
import com.ayushsingh.cacmp_backend.util.exceptionUtil.ApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserRoleService userRoleService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Boolean isUserPresent(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public String registerUser(UserRegisterDto userDto) {
        String username=userDto.getUsername();
        Boolean isUserPresent = isUserPresent(username);
        User user=new User();
        Set<UserRole> roles=new HashSet<>();
        if(isUserPresent){
            throw new ApiException("User with username: "+username+" already exists");
        }
        else{
            Set<String> userRoles=userDto.getRoles();
            for(String userRole: userRoles){
                UserRole role=userRoleService.getUserRoleByRoleName(userRole);
                roles.add(role);
            }
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            user.setRoles(roles);
            user=userRepository.save(user);
            return user.getUserToken();
        }
    }

    @Override
    public String getUserToken(String username) {
        return userRepository.findTokenByUsername(username);
    }


}
