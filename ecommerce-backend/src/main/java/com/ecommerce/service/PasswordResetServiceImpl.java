package com.ecommerce.service;

import com.ecommerce.dto.ForgotPasswordRequest;
import com.ecommerce.dto.ResetPasswordRequest;
import com.ecommerce.dto.VerifyOtpRequest;
import com.ecommerce.entity.PasswordResetOtp;
import com.ecommerce.entity.User;
import com.ecommerce.repository.PasswordResetOtpRepository;
import com.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class PasswordResetServiceImpl implements PasswordResetService {

    private final PasswordResetOtpRepository otpRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Override
    public String sendOtp(ForgotPasswordRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        String otp = String.format("%06d",
                new Random().nextInt(999999));

        PasswordResetOtp resetOtp =
                otpRepository.findByEmail(request.getEmail())
                        .orElse(new PasswordResetOtp());

        resetOtp.setEmail(request.getEmail());
        resetOtp.setOtp(otp);
        resetOtp.setVerified(false);
        resetOtp.setExpiryTime(LocalDateTime.now().plusMinutes(5));

        otpRepository.save(resetOtp);

        emailService.sendOtp(request.getEmail(), otp);

        return "OTP sent successfully";
    }

    @Override
    public String verifyOtp(VerifyOtpRequest request) {

        PasswordResetOtp resetOtp =
                otpRepository.findByEmailAndOtp(
                                request.getEmail(),
                                request.getOtp())
                        .orElseThrow(() ->
                                new RuntimeException("Invalid OTP"));

        if (resetOtp.getExpiryTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP Expired");
        }

        resetOtp.setVerified(true);

        otpRepository.save(resetOtp);

        return "OTP Verified Successfully";
    }

    @Override
    public String resetPassword(ResetPasswordRequest request) {

        PasswordResetOtp resetOtp =
                otpRepository.findByEmailAndOtp(
                                request.getEmail(),
                                request.getOtp())
                        .orElseThrow(() ->
                                new RuntimeException("Invalid OTP"));

        if (!resetOtp.isVerified()) {
            throw new RuntimeException("OTP Not Verified");
        }

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("User Not Found"));

        user.setPassword(
                passwordEncoder.encode(
                        request.getNewPassword()));

        userRepository.save(user);

        otpRepository.delete(resetOtp);

        return "Password Reset Successfully";

    }

}