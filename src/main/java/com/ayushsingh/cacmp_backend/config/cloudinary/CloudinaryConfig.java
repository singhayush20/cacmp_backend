package com.ayushsingh.cacmp_backend.config.cloudinary;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class CloudinaryConfig {

   private final CloudinaryConfigurationProperties cloudinaryConfigurationProperties;


    @Bean
    public Cloudinary getCloudinary() {
        Map<String,String> config=new HashMap<>();
        config.put("cloud_name", cloudinaryConfigurationProperties.cloudName());
        config.put("api_key", cloudinaryConfigurationProperties.apiKey());
        config.put("api_secret", cloudinaryConfigurationProperties.apiSecret());
        config.put("secure","true");
        return new Cloudinary(config);
    }
}
