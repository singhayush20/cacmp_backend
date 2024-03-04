package com.ayushsingh.cacmp_backend.services;

import com.ayushsingh.cacmp_backend.models.dtos.userDtos.UserDetailsDto;
import com.ayushsingh.cacmp_backend.models.dtos.userDtos.UserRegisterDto;

import java.util.List;

public interface UserService {

    public Boolean isUserPresent(String username);

    public String registerUser(UserRegisterDto userDto);

    public String getUserToken(String username);


    List<UserDetailsDto> listAllUsers();

    void deleteUser(String userToken);
}
