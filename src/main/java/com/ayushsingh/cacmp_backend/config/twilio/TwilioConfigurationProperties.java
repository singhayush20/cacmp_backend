package com.ayushsingh.cacmp_backend.config.twilio;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("twilio")
public record TwilioConfigurationProperties(String accountSid, String authToken, String serviceSid) {
}
