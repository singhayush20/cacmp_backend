package com.ayushsingh.cacmp_backend.models.dtos.authDtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenDto {

    private String refreshToken;
    private String username;
}
