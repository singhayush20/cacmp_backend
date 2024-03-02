package com.ayushsingh.cacmp_backend.models.dtos.consumerAddressDtos;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConsumerAddressCreateDto {

    private String houseNo;

    private String locality;

    private String wardNo;

    private Long pincode;

    private String city;

    private String state;
}
