package com.ayushsingh.cacmp_backend.config.drive;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("drive")
public record DriveConfigurationProperties(String folderId, String urlPrefix) {
}
