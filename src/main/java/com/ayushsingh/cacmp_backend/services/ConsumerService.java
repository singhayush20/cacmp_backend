package com.ayushsingh.cacmp_backend.services;

import com.ayushsingh.cacmp_backend.models.dtos.consumerDtos.ConsumerDetailsDto;
import com.ayushsingh.cacmp_backend.models.projections.consumer.ConsumerDetailsProjection;

public interface ConsumerService {

    Boolean isConsumerPresent(String email);

    String registerConsumer(ConsumerDetailsDto consumerDetailsDto);

    String getConsumerToken(String email);

    String updateConsumer(ConsumerDetailsDto consumerDto, String userToken);

    ConsumerDetailsProjection getConsumer(String token);
}
