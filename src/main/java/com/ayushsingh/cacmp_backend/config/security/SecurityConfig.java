package com.ayushsingh.cacmp_backend.config.security;

import com.ayushsingh.cacmp_backend.config.security.authProvider.ConsumerAuthProvider;
import com.ayushsingh.cacmp_backend.config.security.authProvider.DepartmentAuthProvider;
import com.ayushsingh.cacmp_backend.config.security.authProvider.UserAuthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String[] PUBLIC_URLS = {
            "/api/v1/consumer/login",
            "/api/v1/consumer/register",
            "/api/v1/consumer/refresh-token",
            "/api/v1/department/login",
            "/api/v1/department/register",
            "/api/v1/department/refresh-token",
            "/api/v1/user/login",
            "/api/v1/user/register",
            "/api/v1/user/refresh-token"
    };


    private final AuthenticationEntryPoint authEntryPoint;
    private final ConsumerAuthProvider consumerAuthProvider;
    private final DepartmentAuthProvider departmentAuthProvider;
    private final UserAuthProvider userAuthProvider;


    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(consumerAuthProvider).authenticationProvider(userAuthProvider).authenticationProvider(departmentAuthProvider);

        return authenticationManagerBuilder.build();
    }


    @Bean
    public CustomAuthFilter customAuthenticationFilter(HttpSecurity http) throws Exception {
        return new CustomAuthFilter(authManager(http));
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.cors().configurationSource(request -> {
            CorsConfiguration configuration = new CorsConfiguration();
            configuration.setAllowedOrigins(List.of("http://localhost:8085"));
            configuration.setAllowedMethods(List.of("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
            configuration.setAllowCredentials(true);
            configuration.addExposedHeader("Message");
            configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
            return configuration;
        }).and().csrf().disable().authorizeHttpRequests().requestMatchers(PUBLIC_URLS).permitAll().anyRequest().authenticated().and().exceptionHandling().authenticationEntryPoint(authEntryPoint).and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);


        http.addFilterBefore(customAuthenticationFilter(http), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }


    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }
}
