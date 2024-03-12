package com.ayushsingh.cacmp_backend;

import com.ayushsingh.cacmp_backend.config.cloudinary.CloudinaryConfigurationProperties;
import com.ayushsingh.cacmp_backend.config.email.EmailConfigurationProperties;
import com.ayushsingh.cacmp_backend.config.twilio.TwilioConfigurationProperties;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@RequiredArgsConstructor
@EnableConfigurationProperties({CloudinaryConfigurationProperties.class, EmailConfigurationProperties.class, TwilioConfigurationProperties.class})
public class CacmpBackendApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(CacmpBackendApplication.class, args);
	}


	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Application is running...");
	}
}
