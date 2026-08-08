package com.ecommerce.service;

import com.ecommerce.dto.PaymentResponse;
import com.ecommerce.dto.PaymentVerifyRequest;
import com.ecommerce.dto.RazorpayOrderRequest;
import com.ecommerce.dto.RazorpayOrderResponse;

import java.util.List;

public interface PaymentService {

    RazorpayOrderResponse createRazorpayOrder(
            RazorpayOrderRequest request
    );

    String verifyPayment(
            PaymentVerifyRequest request
    );

    PaymentResponse getPaymentById(
            Long paymentId
    );

    List<PaymentResponse> getMyPayments();

}