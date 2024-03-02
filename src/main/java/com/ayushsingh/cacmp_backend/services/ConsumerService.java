package com.ayushsingh.cacmp_backend.services;

import com.ayushsingh.cacmp_backend.models.dtos.consumerDtos.ConsumerRegisterDto;

public interface ConsumerService {

    public Boolean isConsumerPresent(String email);

    public String registerConsumer(ConsumerRegisterDto consumerRegisterDto);
}
