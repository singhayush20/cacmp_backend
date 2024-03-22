package com.ayushsingh.cacmp_backend.models.dtos.consumerDtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordChangeDto {

    String email;

    Long phone;

    String password;

    Integer otp;
}
