package com.ayushsingh.cacmp_backend.services;

import com.ayushsingh.cacmp_backend.models.dtos.consumerDtos.ConsumerRegisterDto;

public interface ConsumerService {

    Boolean isConsumerPresent(String email);

    String registerConsumer(ConsumerRegisterDto consumerRegisterDto);

    String getConsumerToken(String email);
}
