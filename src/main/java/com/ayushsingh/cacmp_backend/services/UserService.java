package com.ayushsingh.cacmp_backend.services;

import com.ayushsingh.cacmp_backend.models.dtos.userDtos.UserRegisterDto;

public interface UserService {

    public Boolean isUserPresent(String username);

    public String registerUser(UserRegisterDto userDto);


}
