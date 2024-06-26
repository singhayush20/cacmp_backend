package com.ayushsingh.cacmp_backend.models.dtos.consumerAddressDtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConsumerAddressDto {

    private String houseNo;

    private String locality;

    private String wardNo;

    private Long pinCode;

    private String city;

    private String state;
}
