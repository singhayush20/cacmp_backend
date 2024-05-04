package com.ayushsingh.cacmp_backend.models.dtos.userDtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPasswordResetDto {
    private String email;
    private String newPassword;
    private Integer otp;
}
