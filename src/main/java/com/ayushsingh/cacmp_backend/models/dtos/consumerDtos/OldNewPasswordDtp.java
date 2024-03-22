package com.ayushsingh.cacmp_backend.models.dtos.consumerDtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OldNewPasswordDtp {

    private String oldPassword;
    private String newPassword;
    private String consumerToken;
}
