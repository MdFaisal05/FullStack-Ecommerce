package com.ecommerce.service;

import com.ecommerce.dto.PaymentResponse;
import com.ecommerce.dto.PaymentVerifyRequest;
import com.ecommerce.dto.RazorpayOrderRequest;
import com.ecommerce.dto.RazorpayOrderResponse;
import com.ecommerce.entity.Payment;
import com.ecommerce.entity.PaymentStatus;
import com.ecommerce.repository.PaymentRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.Utils;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.ecommerce.service.EmailService;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final RazorpayClient razorpayClient;

    private final PaymentRepository paymentRepository;

    private final EmailService emailService;

    @Value("${razorpay.key-id}")
    private String keyId;

    @Value("${razorpay.key-secret}")
    private String keySecret;

    // ============================================
    // Payment -> DTO
    // ============================================

    private PaymentResponse mapPayment(Payment payment) {

        return PaymentResponse.builder()
                .paymentId(payment.getId())
                .orderId(payment.getOrder().getId())
                .amount(payment.getAmount())
                .paymentMethod(payment.getPaymentMethod())
                .paymentStatus(payment.getPaymentStatus())
                .razorpayOrderId(payment.getRazorpayOrderId())
                .razorpayPaymentId(payment.getRazorpayPaymentId())
                .razorpaySignature(payment.getRazorpaySignature())
                .createdAt(payment.getCreatedAt())
                .build();
    }

    // ============================================
    // Create Razorpay Order
    // ============================================

    @Override
    public RazorpayOrderResponse createRazorpayOrder(
            RazorpayOrderRequest request
    ) {

        try {

            int amount = request.getAmount()
                    .multiply(BigDecimal.valueOf(100))
                    .intValue();

            JSONObject options = new JSONObject();

            options.put("amount", amount);
            options.put("currency", "INR");
            options.put("receipt", "receipt_" + System.currentTimeMillis());

            Order order = razorpayClient.orders.create(options);

            return RazorpayOrderResponse.builder()
                    .orderId(order.get("id"))
                    .amount(order.get("amount"))
                    .currency(order.get("currency"))
                    .razorpayKey(keyId)
                    .build();

        } catch (Exception e) {

            throw new RuntimeException(
                    "Unable To Create Razorpay Order",
                    e
            );

        }

    }

    // ============================================
    // Verify Razorpay Payment
    // ============================================

    @Override
    public String verifyPayment(
            PaymentVerifyRequest request
    ) {

        try {

            JSONObject json = new JSONObject();

            json.put("razorpay_order_id",
                    request.getRazorpayOrderId());

            json.put("razorpay_payment_id",
                    request.getRazorpayPaymentId());

            json.put("razorpay_signature",
                    request.getRazorpaySignature());

            boolean verified =
                    Utils.verifyPaymentSignature(
                            json,
                            keySecret
                    );

            if (!verified) {

                throw new RuntimeException(
                        "Payment Verification Failed"
                );

            }

            Payment payment = paymentRepository
                    .findByRazorpayPaymentId(
                            request.getRazorpayPaymentId()
                    )
                    .orElse(null);

            if (payment != null) {

                payment.setPaymentStatus(
                        PaymentStatus.SUCCESS
                );

                payment.setRazorpaySignature(
                        request.getRazorpaySignature()
                );

                paymentRepository.save(payment);

                emailService.sendPaymentSuccessEmail(

                        payment.getUser().getEmail(),

                        payment.getOrder().getId()

                );

            }

            return "Payment Verified Successfully";

        } catch (Exception e) {

            throw new RuntimeException(
                    "Payment Verification Failed",
                    e
            );

        }

    }

    // ============================================
    // Get Payment By Id
    // ============================================

    @Override
    public PaymentResponse getPaymentById(Long paymentId) {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() ->
                        new RuntimeException("Payment Not Found"));

        return mapPayment(payment);

    }

    // ============================================
    // Logged In User Payments
    // ============================================

    @Override
    public List<PaymentResponse> getMyPayments() {

        return paymentRepository.findAll()
                .stream()
                .map(this::mapPayment)
                .toList();

    }

}