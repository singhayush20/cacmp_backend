package com.ayushsingh.cacmp_backend.config.email;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("email")
public record EmailConfigurationProperties(String username, String password) {
}
