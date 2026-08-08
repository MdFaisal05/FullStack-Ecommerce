package com.ecommerce.controller;

import com.ecommerce.dto.PaymentVerifyRequest;
import com.ecommerce.dto.RazorpayOrderRequest;
import com.ecommerce.dto.RazorpayOrderResponse;
import com.ecommerce.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    // ==========================================
    // Create Razorpay Order
    // POST : /api/payments/razorpay-order
    // ==========================================

    @PostMapping("/razorpay-order")
    public ResponseEntity<RazorpayOrderResponse> createRazorpayOrder(

            @Valid
            @RequestBody
            RazorpayOrderRequest request

    ) {

        return ResponseEntity.ok(

                paymentService.createRazorpayOrder(request)

        );

    }

    // ==========================================
    // Verify Razorpay Payment
    // POST : /api/payments/verify
    // ==========================================

    @PostMapping("/verify")
    public ResponseEntity<String> verifyPayment(

            @Valid
            @RequestBody
            PaymentVerifyRequest request

    ) {

        return ResponseEntity.ok(

                paymentService.verifyPayment(request)

        );

    }

}