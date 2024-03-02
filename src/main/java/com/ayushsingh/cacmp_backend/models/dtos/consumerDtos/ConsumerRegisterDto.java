package com.ayushsingh.cacmp_backend.models.dtos.consumerDtos;

import com.ayushsingh.cacmp_backend.models.dtos.consumerAddressDtos.ConsumerAddressCreateDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConsumerRegisterDto {
    private String name;

    private String email;

    private String password;

    private String phone;

    private String gender;

    private ConsumerAddressCreateDto address;

    private Set<String> roles;

}
