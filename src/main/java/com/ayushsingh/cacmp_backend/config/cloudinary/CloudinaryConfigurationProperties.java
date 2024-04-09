package com.ayushsingh.cacmp_backend.config.cloudinary;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;

@ConfigurationProperties("cloudinary")
public record CloudinaryConfigurationProperties(String cloudName, String apiKey, String apiSecret) {
}
