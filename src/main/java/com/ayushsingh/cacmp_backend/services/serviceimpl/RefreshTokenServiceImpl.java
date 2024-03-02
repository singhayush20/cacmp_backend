package com.ayushsingh.cacmp_backend.services.serviceimpl;

import com.ayushsingh.cacmp_backend.constants.AppConstants;
import com.ayushsingh.cacmp_backend.models.entities.Consumer;
import com.ayushsingh.cacmp_backend.models.entities.Department;
import com.ayushsingh.cacmp_backend.models.entities.User;
import com.ayushsingh.cacmp_backend.models.securityModels.jwt.ConsumerRefreshToken;
import com.ayushsingh.cacmp_backend.models.securityModels.jwt.DepartmentRefreshToken;
import com.ayushsingh.cacmp_backend.models.securityModels.jwt.RefreshToken;
import com.ayushsingh.cacmp_backend.models.securityModels.jwt.UserRefreshToken;
import com.ayushsingh.cacmp_backend.repository.entities.ConsumerRepository;
import com.ayushsingh.cacmp_backend.repository.entities.DepartmentRepository;
import com.ayushsingh.cacmp_backend.repository.entities.UserRepository;
import com.ayushsingh.cacmp_backend.repository.jwt.RefreshTokenRepository;
import com.ayushsingh.cacmp_backend.services.RefreshTokenService;
import com.ayushsingh.cacmp_backend.util.exceptionUtil.ApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final ConsumerRepository consumerRepository;
    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;

    @Override
    public RefreshToken createRefreshToken(String username, String entityType) {
        if (entityType.equals(AppConstants.ENTITY_TYPE_DEPARTMENT)) {
            return getDepartmentRefreshToken(username);
        }
        else if (entityType.equals(AppConstants.ENTITY_TYPE_USER)) {
            return getUserRefreshToken(username);
        } else if (entityType.equals(AppConstants.ENTITY_TYPE_CONSUMER)) {
            return getConsumerRefreshToken(username);
        }
        throw new ApiException("Invalid entity type: " + entityType);
    }

    private RefreshToken getConsumerRefreshToken(String username) {
        Consumer consumer = this.consumerRepository.findByEmail(username).orElseThrow(() -> new ApiException("User with username: " + username + " does not exist"));
        Optional<RefreshToken> refreshTokenOptional = this.refreshTokenRepository.findByTypeAndId(AppConstants.ENTITY_TYPE_CONSUMER, consumer.getConsumerId());
        if (refreshTokenOptional.isPresent()) {
            RefreshToken refreshToken = refreshTokenOptional.get();
            refreshToken.setExpiresAt(Instant.now().plusMillis(AppConstants.REFRESH_TOKEN_EXPIRATION_TIME));
            refreshToken = refreshTokenRepository.save(refreshToken);
            return refreshToken;
        } else {
            ConsumerRefreshToken consumerRefreshToken = new ConsumerRefreshToken();
            consumerRefreshToken.setConsumer(this.consumerRepository.findByEmail(username).orElseThrow(() -> new ApiException("User with username: " + username + " does not exist")));
            consumerRefreshToken.setExpiresAt(Instant.now().plusMillis(AppConstants.REFRESH_TOKEN_EXPIRATION_TIME));
            consumerRefreshToken.setRefreshToken(UUID.randomUUID().toString());
            consumerRefreshToken = refreshTokenRepository.save(consumerRefreshToken);
            return consumerRefreshToken;
        }
    }

    private RefreshToken getDepartmentRefreshToken(String username) {
        Department department = this.departmentRepository.findByUsername(username).orElseThrow(() -> new ApiException("Department with username: " + username + " does not exist"));
        Optional<RefreshToken> refreshTokenOptional = this.refreshTokenRepository.findByTypeAndId(AppConstants.ENTITY_TYPE_DEPARTMENT, department.getDepartmentId());
        if (refreshTokenOptional.isPresent()) {
            RefreshToken refreshToken = refreshTokenOptional.get();
            refreshToken.setExpiresAt(Instant.now().plusMillis(AppConstants.REFRESH_TOKEN_EXPIRATION_TIME));
            refreshToken = refreshTokenRepository.save(refreshToken);
            return refreshToken;
        } else {
            DepartmentRefreshToken departmentRefreshToken = new DepartmentRefreshToken();
            departmentRefreshToken.setDepartment(department);
            departmentRefreshToken.setExpiresAt(Instant.now().plusMillis(AppConstants.REFRESH_TOKEN_EXPIRATION_TIME));
            departmentRefreshToken.setRefreshToken(UUID.randomUUID().toString());
            departmentRefreshToken = refreshTokenRepository.save(departmentRefreshToken);
            return departmentRefreshToken;
        }
    }

    private RefreshToken getUserRefreshToken(String username) {
        User user=this.userRepository.findByUsername(username).orElseThrow(() -> new ApiException("User with username: " + username + " does not exist"));
        Optional<RefreshToken> refreshTokenOptional = this.refreshTokenRepository.findByTypeAndId(AppConstants.ENTITY_TYPE_USER, user.getUserId());
        if (refreshTokenOptional.isPresent()) {
            RefreshToken refreshToken = refreshTokenOptional.get();
            refreshToken.setExpiresAt(Instant.now().plusMillis(AppConstants.REFRESH_TOKEN_EXPIRATION_TIME));
            refreshToken = refreshTokenRepository.save(refreshToken);
            return refreshToken;
        } else {
            UserRefreshToken userRefreshToken = new UserRefreshToken();
            userRefreshToken.setUser(this.userRepository.findByUsername(username).orElseThrow(() -> new ApiException("User with username: " + username + " does not exist")));
            userRefreshToken.setExpiresAt(Instant.now().plusMillis(AppConstants.REFRESH_TOKEN_EXPIRATION_TIME));
            userRefreshToken.setRefreshToken(UUID.randomUUID().toString());
            userRefreshToken = refreshTokenRepository.save(userRefreshToken);
            return userRefreshToken;
        }
    }

    @Override
    public Boolean verifyRefreshToken(String refreshToken) {
        RefreshToken token=this.refreshTokenRepository.findByRefreshToken(refreshToken).orElseThrow(()->new RuntimeException("No such token found"));
        if (token.getExpiresAt().isBefore(Instant.now())) {
            refreshTokenRepository.delete(token);
            return false;
        }
        return true;
    }
}
