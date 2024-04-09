package com.ayushsingh.cacmp_backend.config.awsS3Config;

import com.google.api.client.util.Value;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


@ConfigurationProperties(prefix = "aws")
public record AwsS3ConfigurationProperties(String accessKey,  String secretKey,String bucketName, String region) {
}
