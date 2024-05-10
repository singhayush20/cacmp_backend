package com.ayushsingh.cacmp_backend.services.serviceimpl;

import com.ayushsingh.cacmp_backend.config.email.EmailConfigurationProperties;
import com.ayushsingh.cacmp_backend.config.twilio.TwilioConfigurationProperties;
import com.ayushsingh.cacmp_backend.models.dtos.consumerDtos.ConsumerDetailsDto;
import com.ayushsingh.cacmp_backend.models.dtos.consumerDtos.OldNewPasswordDtp;
import com.ayushsingh.cacmp_backend.models.dtos.consumerDtos.PasswordChangeDto;
import com.ayushsingh.cacmp_backend.models.entities.Consumer;
import com.ayushsingh.cacmp_backend.models.entities.ConsumerAddress;
import com.ayushsingh.cacmp_backend.models.projections.consumer.ConsumerDetailsProjection;
import com.ayushsingh.cacmp_backend.models.roles.ConsumerRole;
import com.ayushsingh.cacmp_backend.repository.entities.ConsumerRepository;
import com.ayushsingh.cacmp_backend.services.ConsumerRoleService;
import com.ayushsingh.cacmp_backend.services.ConsumerService;
import com.ayushsingh.cacmp_backend.util.emailUtil.AccountVerificationEmailContext;
import com.ayushsingh.cacmp_backend.util.emailUtil.MailService;
import com.ayushsingh.cacmp_backend.util.exceptionUtil.ApiException;
import com.ayushsingh.cacmp_backend.util.otpUtil.OtpService;
import com.twilio.Twilio;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConsumerServiceImpl implements ConsumerService {

    private final ConsumerRepository consumerRepository;
    private final ConsumerRoleService consumerRoleService;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final MailService mailService;
    private final OtpService otpService;
    private final EmailConfigurationProperties emailConfigurationProperties;
    private final TwilioConfigurationProperties twilioConfigurationProperties;

    @Override
    public Boolean isConsumerPresent(String email) {
        return consumerRepository.existsByEmail(email);
    }

    @Transactional
    @Override
    public String registerConsumer(ConsumerDetailsDto consumerDetailsDto) {
        String email = consumerDetailsDto.getEmail();
        Boolean isConsumerPresent = isConsumerPresent(email);
        if (isConsumerPresent) {
            throw new ApiException("Consumer with email: " + email + " already exists");
        } else {
            Consumer consumer = new Consumer();
            Set<ConsumerRole> roles = new HashSet<>();
            Set<String> consumerRoles = consumerDetailsDto.getRoles();
            for (String consumerRole : consumerRoles) {
                ConsumerRole role = consumerRoleService.getConsumerRoleByRoleName(consumerRole);
                roles.add(role);
            }
            consumer.setEmail(email);
            consumer.setPassword(passwordEncoder.encode(consumerDetailsDto.getPassword()));
            consumer.setRoles(roles);
            consumer.setPhone(consumerDetailsDto.getPhone());
            consumer.setGender(consumerDetailsDto.getGender());
            consumer.setName(consumerDetailsDto.getName());
            consumer.setIsEmailVerified(false);
            if (consumerDetailsDto.getAddress() != null) {
                ConsumerAddress consumerAddress = this.modelMapper.map(consumerDetailsDto.getAddress(), ConsumerAddress.class);
                consumer.setAddress(consumerAddress);
            }
            consumer = consumerRepository.save(consumer);
            return consumer.getConsumerToken();
        }
    }

    @Override
    public String getConsumerToken(String email) {
        return consumerRepository.findTokenByEmail(email);
    }

    @Override
    public String updateConsumer(ConsumerDetailsDto consumerDto, String userToken) {
        Consumer consumer = consumerRepository.findByUserToken(userToken).orElseThrow(() -> new ApiException("Consumer with email: " + consumerDto.getEmail() + " does not exist"));
        consumer.setGender(consumerDto.getGender());
        consumer.setName(consumerDto.getName());
        consumer.setAddress(this.modelMapper.map(consumerDto.getAddress(), ConsumerAddress.class));
        consumerRepository.save(consumer);
        return consumer.getConsumerToken();
    }

    @Override
    public ConsumerDetailsProjection getConsumer(String token) {
        return consumerRepository.getConsumerDetails(token);
    }

    @Override
    public void sendVerificationEmail(String email) {
        int otp = otpService.generateOTP(email);
        AccountVerificationEmailContext emailContext = new AccountVerificationEmailContext(emailConfigurationProperties);
        emailContext.init();
        emailContext.setTo(email);
        emailContext.setOTP(String.valueOf(otp));
        try {
            mailService.sendEmail(emailContext);
        } catch (MessagingException e) {
            log.error("Some error occurred while sending email: " + e.getMessage());
        }
    }

    @Override
    public void verifyEmailOTP(String email, int otp) {
        int storedOTP = otpService.getOTP(email);
        log.info("Stored otp: " + storedOTP + " received otp: " + otp);
        if (storedOTP != otp) {
            throw new ApiException("The otp is either incorrect or expired!");
        }
        otpService.clearOTP(email);
    }

    @Override
    public void sendPhoneVerificationOTP(Long phone) {
        Twilio.init(twilioConfigurationProperties.accountSid(), twilioConfigurationProperties.authToken());
        Verification verification = Verification.creator(twilioConfigurationProperties.serviceSid(),
                        "+91"+phone.toString(),
                        "sms")
                .create();
        log.info("otp sent to: {}", phone);
    }

    @Override
    public void verifyPhoneOTP(Long phone, Integer otp) {
        try {
            VerificationCheck verificationCheck = VerificationCheck.creator(
                            twilioConfigurationProperties.serviceSid())
                    .setTo("+91"+phone.toString())
                    .setCode(otp.toString())
                    .create();
            log.info("verification status: {}", verificationCheck.getStatus());
        } catch (Exception e) {
            throw new ApiException("The otp is either incorrect or expired!");
        }
    }

    @Override
    public String sendPasswordResetOTP(String email, Long phone) {
        if (email != null) {
            if (consumerRepository.existsByEmail(email)) {
                sendVerificationEmail(email);
                return "OTP sent to " + email;
            } else throw new ApiException("No consumer found with email: " + email);
        } else if (phone != null) {
            if (consumerRepository.existsByPhone(phone.toString())) {
                sendPhoneVerificationOTP(phone);
                return "OTP sent to " + phone;
            } else throw new ApiException("No consumer found with phone number: " + phone);

        }
        throw new ApiException("Neither email nor phone is present");
    }

    @Override
    public String changePassword(PasswordChangeDto passwordChangeDto) {
        String password = passwordChangeDto.getPassword();
        String email = passwordChangeDto.getEmail();
        Long phone = passwordChangeDto.getPhone();
        Integer otp = passwordChangeDto.getOtp();
        if (email != null) {
            Consumer consumer = consumerRepository.findByEmail(email).orElseThrow(() -> new ApiException("No consumer found with email: " + email));
            int storedOTP = otpService.getOTP(email);
            log.info("Stored otp: " + storedOTP + " received otp: " + otp);
            if (storedOTP != otp) {
                throw new ApiException("The otp is either incorrect or expired!");
            }
            otpService.clearOTP(email);
            consumer.setPassword(passwordEncoder.encode(password));
            return consumerRepository.save(consumer).getConsumerToken();
        } else if (phone != null) {
            Consumer consumer = consumerRepository.findByPhone(phone.toString()).orElseThrow(() -> new ApiException("No consumer found with phone number: " + phone));
            try {
                VerificationCheck verificationCheck = VerificationCheck.creator(
                                twilioConfigurationProperties.serviceSid())
                        .setTo(phone.toString())
                        .setCode(otp.toString())
                        .create();
                log.info("verification status: {}", verificationCheck.getStatus());
                consumer.setPassword(passwordEncoder.encode(password));
                return consumerRepository.save(consumer).getConsumerToken();
            } catch (Exception e) {
                throw new ApiException("The otp is either incorrect or expired!");
            }
        }
        throw new ApiException("Neither email nor phone is present");
    }

    @Override
    public String changePassword(OldNewPasswordDtp passwordDto) {
        String oldPassword = passwordDto.getOldPassword();
        String newPassword = passwordDto.getNewPassword();
        String consumerToken = passwordDto.getConsumerToken();
        Consumer consumer = this.consumerRepository.findByUserToken(consumerToken).orElseThrow(() -> new ApiException("No consumer found with token: " + consumerToken));
        if (passwordEncoder.matches(oldPassword, consumer.getPassword())) {
            consumer.setPassword(passwordEncoder.encode(newPassword));
            return consumerRepository.save(consumer).getConsumerToken();
        } else {
            throw new ApiException("Old password is incorrect");
        }
    }


}
