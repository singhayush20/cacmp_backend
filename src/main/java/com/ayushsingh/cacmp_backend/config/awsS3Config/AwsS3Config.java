package com.ayushsingh.cacmp_backend.config.awsS3Config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
@Configuration
@RequiredArgsConstructor
public class AwsS3Config {

    private final AwsS3ConfigurationProperties awsS3ConfigurationProperties;

    @Bean("s3-bucket-client")
    public S3Client createS3Client() {
        AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(
                awsS3ConfigurationProperties.accessKey(),
                awsS3ConfigurationProperties.secretKey());
        return S3Client.builder()
                .region(Region.of(awsS3ConfigurationProperties.region()))
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                .build();
    }
}
