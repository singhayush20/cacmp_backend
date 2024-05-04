package com.ayushsingh.cacmp_backend.services.serviceimpl;

import com.ayushsingh.cacmp_backend.config.email.EmailConfigurationProperties;
import com.ayushsingh.cacmp_backend.models.dtos.userDtos.UserDetailsDto;
import com.ayushsingh.cacmp_backend.models.dtos.userDtos.UserPasswordResetDto;
import com.ayushsingh.cacmp_backend.models.dtos.userDtos.UserRegisterDto;
import com.ayushsingh.cacmp_backend.models.entities.User;
import com.ayushsingh.cacmp_backend.models.roles.UserRole;
import com.ayushsingh.cacmp_backend.repository.entities.UserRepository;
import com.ayushsingh.cacmp_backend.services.UserRoleService;
import com.ayushsingh.cacmp_backend.services.UserService;
import com.ayushsingh.cacmp_backend.util.emailUtil.AccountVerificationEmailContext;
import com.ayushsingh.cacmp_backend.util.emailUtil.MailService;
import com.ayushsingh.cacmp_backend.util.exceptionUtil.ApiException;
import com.ayushsingh.cacmp_backend.util.otpUtil.OtpService;

import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRoleService userRoleService;
    private final PasswordEncoder passwordEncoder;
    private final OtpService otpService;
    private final MailService mailService;
    private final EmailConfigurationProperties emailConfigurationProperties;

    @Override
    public Boolean isUserPresent(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public String registerUser(UserRegisterDto userDto) {
        String username = userDto.getUsername();
        Boolean isUserPresent = isUserPresent(username);
        User user = new User();
        Set<UserRole> roles = new HashSet<>();
        if (isUserPresent) {
            throw new ApiException("User with username: " + username + " already exists");
        } else {
            Set<String> userRoles = userDto.getRoles();
            for (String userRole : userRoles) {
                UserRole role = userRoleService.getUserRoleByRoleName(userRole);
                roles.add(role);
            }
            user.setUsername(username);
            user.setEmail(userDto.getEmail());
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            user.setRoles(roles);
            user.setName(userDto.getName());
            user = userRepository.save(user);
            return user.getUserToken();
        }
    }

    @Override
    public String getUserToken(String username) {
        return userRepository.findTokenByUsername(username);
    }

    @Override
    public List<UserDetailsDto> listAllUsers() {
        return userRepository.findAll().stream().map(user -> {
            UserDetailsDto userDetailsDto = new UserDetailsDto();
            userDetailsDto.setUserToken(user.getUserToken());
            userDetailsDto.setUsername(user.getUsername());
            userDetailsDto.setName(user.getName());
            Set<UserRole> roles = user.getRoles();
            Set<String> userRoles = new HashSet<>();
            for (UserRole role : roles) {
                userRoles.add(role.getRoleName());
            }
            userDetailsDto.setRoles(userRoles);
            return userDetailsDto;
        }).toList();
    }

    @Transactional
    @Override
    public void deleteUser(String userToken) {
        userRepository.deleteByUserToken(userToken);
    }

    @Override
    public UserDetailsDto getUser(String userToken) {
        Optional<User> userOptional = userRepository.findByUserToken(userToken);
        if (userOptional.isEmpty()) {
            throw new ApiException("User with user token: " + userToken + " does not exist");
        } else {
            UserDetailsDto userDetailsDto = new UserDetailsDto();
            userDetailsDto.setUserToken(userOptional.get().getUserToken());
            userDetailsDto.setUsername(userOptional.get().getUsername());
            userDetailsDto.setName(userOptional.get().getName());
            userDetailsDto.setEmail(userOptional.get().getEmail());
            Set<String> roles = new HashSet<>();
            Set<UserRole> userRoles = userOptional.get().getRoles();
            for (UserRole role : userRoles) {
                roles.add(role.getRoleName());
            }
            userDetailsDto.setRoles(roles);
            return userDetailsDto;
        }
    }

    @Override
    public String updateUser(UserDetailsDto userDetailsDto) {
        String userToken = userDetailsDto.getUserToken();
        Optional<User> userOptional = userRepository.findByUserToken(userToken);
        if (userOptional.isEmpty()) {
            throw new ApiException("User with user token: " + userToken + " does not exist");
        } else {
            User user = userOptional.get();
            user.setName(userDetailsDto.getName());
            user.setUsername(userDetailsDto.getUsername());
            user.setEmail(userDetailsDto.getEmail());
            Set<UserRole> roles = new HashSet<>();
            Set<String> userRoles = userDetailsDto.getRoles();
            for (String role : userRoles) {
                UserRole userRole = userRoleService.getUserRoleByRoleName(role);
                roles.add(userRole);
            }
            user.setRoles(roles);
            user = userRepository.save(user);
            return user.getUserToken();
        }
    }

    @Override
    public String sendPasswordVerificationOTP(String email) {
        int otp = otpService.generateOTP(email);
        AccountVerificationEmailContext emailContext = new AccountVerificationEmailContext(
                emailConfigurationProperties);
        emailContext.init();
        emailContext.setTo(email);
        emailContext.setOTP(String.valueOf(otp));
        try {
            mailService.sendEmail(emailContext);
        } catch (MessagingException e) {
            log.error("Some error occurred while sending email: " + e.getMessage());
        }
        return "If the email exists in our database, an OTP has been sent to " + email;
    }

    @Override
    public String resetPassword(UserPasswordResetDto userPasswordResetDto) {
        if (otpService.getOTP(userPasswordResetDto.getEmail()) != userPasswordResetDto.getOtp()) {
            throw new ApiException("Invalid OTP");
        }
        otpService.clearOTP(userPasswordResetDto.getEmail());

        Optional<User> userOptional = userRepository.findByEmail(userPasswordResetDto.getEmail());
        if (userOptional.isEmpty()) {
            throw new ApiException(
                    "User with username or email: " + userPasswordResetDto.getEmail() + " does not exist");
        }
        User user = userOptional.get();
        user.setPassword(passwordEncoder.encode(userPasswordResetDto.getNewPassword()));
        user = userRepository.save(user);
        return "Password Reset Successful";
    }
}
