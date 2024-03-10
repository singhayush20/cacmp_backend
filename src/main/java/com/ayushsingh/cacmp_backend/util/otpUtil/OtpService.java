package com.ayushsingh.cacmp_backend.util.otpUtil;

public interface OtpService {
    public int generateOTP(String key);
    public int getOTP(String key);
    public void clearOTP(String key);
}
