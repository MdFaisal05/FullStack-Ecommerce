package com.ecommerce.service;

import com.ecommerce.dto.ForgotPasswordRequest;
import com.ecommerce.dto.ResetPasswordRequest;
import com.ecommerce.dto.VerifyOtpRequest;

public interface PasswordResetService {

    String sendOtp(ForgotPasswordRequest request);

    String verifyOtp(VerifyOtpRequest request);

    String resetPassword(ResetPasswordRequest request);


}