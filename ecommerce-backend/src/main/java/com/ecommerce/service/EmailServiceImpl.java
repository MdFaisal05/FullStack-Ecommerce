package com.ecommerce.service;

import com.ecommerce.entity.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import org.springframework.core.io.ByteArrayResource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    // =====================================================
    // Load HTML Template
    // =====================================================

    private String loadTemplate(String fileName) {

        try {

            ClassPathResource resource =
                    new ClassPathResource(
                            "templates/" + fileName
                    );

            byte[] bytes = resource
                    .getInputStream()
                    .readAllBytes();

            return new String(
                    bytes,
                    StandardCharsets.UTF_8
            );

        } catch (IOException e) {

            throw new RuntimeException(
                    "Unable To Load Email Template",
                    e
            );

        }

    }

    // =====================================================
    // Send HTML Email
    // =====================================================

    private void sendHtmlMail(

            String to,

            String subject,

            String html

    ) {

        try {

            MimeMessage message =
                    mailSender.createMimeMessage();

            MimeMessageHelper helper =
                    new MimeMessageHelper(
                            message,
                            true,
                            "UTF-8"
                    );

            helper.setFrom(fromEmail);

            helper.setTo(to);

            helper.setSubject(subject);

            helper.setText(html, true);

            mailSender.send(message);

        } catch (MessagingException e) {

            throw new RuntimeException(
                    "Unable To Send Email",
                    e
            );

        }

    }

    // =====================================================
    // Order Placed
    // =====================================================

    @Override
    public void sendOrderPlacedEmail(

            String email,

            Long orderId

    ) {

        String html =
                loadTemplate("order-placed.html");

        html = html.replace(
                "{{ORDER_ID}}",
                String.valueOf(orderId)
        );

        sendHtmlMail(

                email,

                "Order Placed Successfully",

                html

        );

    }

    // =====================================================
    // Payment Success
    // =====================================================

    @Override
    public void sendPaymentSuccessEmail(

            String email,

            Long orderId

    ) {

        String html =
                loadTemplate(
                        "payment-success.html"
                );

        html = html.replace(
                "{{ORDER_ID}}",
                String.valueOf(orderId)
        );

        sendHtmlMail(

                email,

                "Payment Successful",

                html

        );

    }

    // =====================================================
    // Order Cancelled
    // =====================================================

    @Override
    public void sendOrderCancelledEmail(

            String email,

            Long orderId

    ) {

        String html =
                loadTemplate(
                        "order-cancelled.html"
                );

        html = html.replace(
                "{{ORDER_ID}}",
                String.valueOf(orderId)
        );

        sendHtmlMail(

                email,

                "Order Cancelled",

                html

        );

    }

    // =====================================================
    // Simple Test Email
    // =====================================================

    @Override
    public void sendSimpleMail(

            String to,

            String subject,

            String body

    ) {

        SimpleMailMessage message =
                new SimpleMailMessage();

        message.setFrom(fromEmail);

        message.setTo(to);

        message.setSubject(subject);

        message.setText(body);

        mailSender.send(message);

    }


    // =====================================================
// Invoice Email With PDF
// =====================================================

    @Override
    public void sendInvoiceEmail(

            String email,

            Long orderId,

            byte[] pdf

    ) {

        try {

            MimeMessage message =
                    mailSender.createMimeMessage();

            MimeMessageHelper helper =
                    new MimeMessageHelper(
                            message,
                            true
                    );

            helper.setFrom(fromEmail);

            helper.setTo(email);

            helper.setSubject(
                    "Invoice - Order #" + orderId
            );

            helper.setText(

                    """
                    Dear Customer,
    
                    Thank you for shopping with us.
    
                    Your invoice is attached with this email.
    
                    Regards,
                    Ecommerce Team
                    """

            );

            helper.addAttachment(

                    "Invoice_" + orderId + ".pdf",

                    new ByteArrayResource(pdf)

            );

            mailSender.send(message);

        }

        catch (Exception e) {

            throw new RuntimeException(

                    "Unable To Send Invoice",

                    e

            );

        }


    }


    // =====================================================
    // Welcome Email
    // =====================================================

    @Override
    public void sendWelcomeEmail(User user) {

        String subject = "Welcome To Ecommerce";

        String body = """
                Hello %s,

                Welcome to our Ecommerce Platform.

                Your account has been created successfully.

                Happy Shopping!

                Regards,
                Ecommerce Team
                """
                .formatted(user.getFirstName());

        sendSimpleMail(
                user.getEmail(),
                subject,
                body
        );

    }

    // =====================================================
    // Send OTP
    // =====================================================

    @Override
    public void sendOtp(
            String email,
            String otp
    ) {

        String subject = "Password Reset OTP";

        String body = """
                Dear Customer,

                Your OTP for password reset is:

                %s

                This OTP is valid for 5 minutes.

                Please do not share this OTP with anyone.

                Regards,
                Ecommerce Team
                """
                .formatted(otp);

        sendSimpleMail(
                email,
                subject,
                body
        );

    }

}
