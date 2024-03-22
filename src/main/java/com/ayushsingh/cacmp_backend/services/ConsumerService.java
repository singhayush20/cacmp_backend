package com.ayushsingh.cacmp_backend.services;

import com.ayushsingh.cacmp_backend.models.dtos.consumerDtos.ConsumerDetailsDto;
import com.ayushsingh.cacmp_backend.models.dtos.consumerDtos.OldNewPasswordDtp;
import com.ayushsingh.cacmp_backend.models.dtos.consumerDtos.PasswordChangeDto;
import com.ayushsingh.cacmp_backend.models.projections.consumer.ConsumerDetailsProjection;

public interface ConsumerService {

    Boolean isConsumerPresent(String email);

    String registerConsumer(ConsumerDetailsDto consumerDetailsDto);

    String getConsumerToken(String email);

    String updateConsumer(ConsumerDetailsDto consumerDto, String userToken);

    ConsumerDetailsProjection getConsumer(String token);

    void sendVerificationEmail(String email);

    void verifyEmailOTP(String email, int otp);

    void sendPhoneVerificationOTP(Long phone);

    void verifyPhoneOTP(Long phone, Integer otp);

    String sendPasswordResetOTP (String email, Long phone);

    String changePassword(PasswordChangeDto passwordChangeDto);

    String changePassword(OldNewPasswordDtp passwordDto);

}
