package com.ecommerce.controller;

import com.ecommerce.dto.OrderResponse;
import com.ecommerce.dto.PlaceOrderRequest;
import com.ecommerce.entity.OrderStatus;
import com.ecommerce.service.OrderService;
import com.ecommerce.service.PDFService;

import com.ecommerce.service.EmailService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import com.ecommerce.dto.OrderTrackingResponse;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    private final PDFService pdfService;

    private final EmailService emailService;

    // ============================================
    // Place Order
    // POST : /api/orders
    // ============================================

    @PostMapping
    public ResponseEntity<OrderResponse> placeOrder(
            @Valid @RequestBody PlaceOrderRequest request
    ) {

        return ResponseEntity.ok(
                orderService.placeOrder(request)
        );

    }

    // ============================================
    // My Orders
    // GET : /api/orders/my-orders
    // ============================================

    @GetMapping("/my-orders")
    public ResponseEntity<List<OrderResponse>> getMyOrders() {

        return ResponseEntity.ok(
                orderService.getMyOrders()
        );

    }

    // ============================================
    // Order Details
    // GET : /api/orders/{id}
    // ============================================

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                orderService.getOrderById(id)
        );

    }

    // ============================================
    // Cancel Order
    // PUT : /api/orders/{id}/cancel
    // ============================================

    @PutMapping("/{id}/cancel")
    public ResponseEntity<String> cancelOrder(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                orderService.cancelOrder(id)
        );

    }

    // ============================================
    // Admin - Get All Orders
    // GET : /api/orders
    // ============================================

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {

        return ResponseEntity.ok(
                orderService.getAllOrders()
        );

    }

    // ============================================
    // Admin - Update Order Status
    // PUT : /api/orders/{id}/status
    // ============================================

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/status")
    public ResponseEntity<OrderResponse> updateOrderStatus(

            @PathVariable Long id,

            @RequestParam OrderStatus status

    ) {

        return ResponseEntity.ok(

                orderService.updateOrderStatus(
                        id,
                        status
                )

        );

    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/{id}/invoice")
    public ResponseEntity<byte[]> downloadInvoice(
            @PathVariable Long id
    ) {

        byte[] pdf = pdfService.generateInvoice(id);

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_PDF);

        headers.setContentDisposition(

                ContentDisposition
                        .attachment()
                        .filename("Invoice_" + id + ".pdf")
                        .build()

        );

        return new ResponseEntity<>(
                pdf,
                headers,
                HttpStatus.OK
        );

    }

// ============================================
// Send Invoice Email
// POST : /api/orders/{id}/send-invoice
// ============================================

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping("/{id}/send-invoice")
    public ResponseEntity<String> sendInvoice(

            @PathVariable Long id,

            @RequestParam String email

    ) {

        byte[] pdf = pdfService.generateInvoice(id);

        emailService.sendInvoiceEmail(

                email,

                id,

                pdf

        );

        return ResponseEntity.ok(

                "Invoice Sent Successfully"

        );

    }

// ============================================
// Track Order
// GET : /api/orders/{id}/track
// ============================================

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/{id}/track")
    public ResponseEntity<OrderTrackingResponse> trackOrder(

            @PathVariable Long id

    ) {

        return ResponseEntity.ok(

                orderService.trackOrder(id)

        );

    }

}