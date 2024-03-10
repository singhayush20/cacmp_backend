package com.ayushsingh.cacmp_backend.models.dtos.consumerDtos;

import com.ayushsingh.cacmp_backend.models.dtos.consumerAddressDtos.ConsumerAddressDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConsumerDetailsDto {
    private String name;

    private String email;

    private String password;

    private String phone;

    private String gender;

    private ConsumerAddressDto address;

    private Set<String> roles;

}
