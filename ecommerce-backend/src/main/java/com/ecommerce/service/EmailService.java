package com.ecommerce.service;

import com.ecommerce.entity.User;

public interface EmailService {

    // ============================================
    // Welcome Email
    // ============================================

    void sendWelcomeEmail(User user);

    // ============================================
    // Send OTP
    // ============================================

    void sendOtp(
            String email,
            String otp
    );

    // ============================================
    // Order Placed
    // ============================================

    void sendOrderPlacedEmail(
            String email,
            Long orderId
    );

    // ============================================
    // Payment Success
    // ============================================

    void sendPaymentSuccessEmail(
            String email,
            Long orderId
    );

    // ============================================
    // Order Cancelled
    // ============================================

    void sendOrderCancelledEmail(
            String email,
            Long orderId
    );

    // ============================================
    // Simple Email
    // ============================================

    void sendSimpleMail(
            String to,
            String subject,
            String body
    );

    // ============================================
    // Invoice Email
    // ============================================

    void sendInvoiceEmail(
            String email,
            Long orderId,
            byte[] pdf
    );

}