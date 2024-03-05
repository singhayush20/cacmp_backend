package com.ayushsingh.cacmp_backend.config.cloudinary;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("cloudinary")
public record CloudinaryConfigurationProperties(String cloudName, String apiKey, String apiSecret) {
}
