package com.ayushsingh.cacmp_backend.util.emailUtil;

import com.ayushsingh.cacmp_backend.config.email.EmailConfigurationProperties;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public  class AccountVerificationEmailContext extends AbstractEmailContext {
    private String token;
    private final EmailConfigurationProperties emailConfigurationProperties;



    @Override
    public  void init() {

        setTemplateLocation("emails/email-verification.html");
        setSubject("Verify your email and complete your registration");
        setFrom(emailConfigurationProperties.username());
        setFromDisplayName("No-reply@cacmp");
    }

 public void setOTP(String otp){
        put("verificationOTP",otp);
 }


}
