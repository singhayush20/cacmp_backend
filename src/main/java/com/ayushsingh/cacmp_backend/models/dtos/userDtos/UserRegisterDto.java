package com.ayushsingh.cacmp_backend.models.dtos.userDtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDto {

    private String username;

    private String password;

    private String name;

    private Set<String> roles;

    private String email;
}
