package com.ayushsingh.cacmp_backend.services.serviceimpl;

import com.ayushsingh.cacmp_backend.models.dtos.userDtos.UserDetailsDto;
import com.ayushsingh.cacmp_backend.models.dtos.userDtos.UserRegisterDto;
import com.ayushsingh.cacmp_backend.models.entities.User;
import com.ayushsingh.cacmp_backend.models.roles.UserRole;
import com.ayushsingh.cacmp_backend.repository.entities.UserRepository;
import com.ayushsingh.cacmp_backend.services.UserRoleService;
import com.ayushsingh.cacmp_backend.services.UserService;
import com.ayushsingh.cacmp_backend.util.exceptionUtil.ApiException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserRoleService userRoleService;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

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
            user.setName(userDto.getName());
            user=userRepository.save(user);
            return user.getUserToken();
        }
    }

    @Override
    public String getUserToken(String username) {
        return userRepository.findTokenByUsername(username);
    }

    @Override
    public List<UserDetailsDto> listAllUsers() {
        return userRepository.findAll().stream().map(user->{
            UserDetailsDto userDetailsDto=new UserDetailsDto();
            userDetailsDto.setUserToken(user.getUserToken());
            userDetailsDto.setUsername(user.getUsername());
            userDetailsDto.setName(user.getName());
            Set<UserRole> roles=user.getRoles();
            Set<String> userRoles=new HashSet<>();
            for(UserRole role: roles){
                userRoles.add(role.getRoleName());
            }
            userDetailsDto.setRoles(userRoles);
            return  userDetailsDto;
        }).toList();
    }

    @Transactional
    @Override
    public void deleteUser(String userToken) {
        userRepository.deleteByUserToken(userToken);
    }


}
