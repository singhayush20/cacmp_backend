package com.ayushsingh.cacmp_backend.util.otpUtil;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class OtpServiceImpl implements OtpService {
    private static final Integer EXPIRE_MINUTES = 10;
    private final LoadingCache<String, Integer> otpCache;

    public OtpServiceImpl() {
        otpCache = CacheBuilder.newBuilder().expireAfterWrite(EXPIRE_MINUTES, TimeUnit.MINUTES)
                .build(
                        new CacheLoader<>() {
                            @Override
                            public Integer load(String key) {
                                return 0;
                            }
                        });
    }

    @Override
    public int generateOTP(String email) {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        log.info("Saving generated otp for id: "+email+" otp: "+otp);
        otpCache.put(email, otp);
        return otp;
    }

    @Override
    public int getOTP(String email) {
        try {
            int otp= otpCache.get(email);
            log.info("Saved otp retrieved: "+otp+" email: "+email);
            return otp;
        } catch (Exception e) {
            log.info("Some error occurred");
            return 0;
        }
    }

    @Override
    public void clearOTP(String email) {
        otpCache.invalidate(email);
    }

}
