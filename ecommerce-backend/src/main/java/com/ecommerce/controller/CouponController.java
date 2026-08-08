package com.ecommerce.controller;

import com.ecommerce.dto.CouponRequest;
import com.ecommerce.dto.CouponResponse;
import com.ecommerce.service.CouponService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/coupons")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    // =====================================================
    // ADMIN
    // Create Coupon
    // =====================================================

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<CouponResponse> createCoupon(
            @Valid @RequestBody CouponRequest request
    ) {

        return ResponseEntity.ok(
                couponService.createCoupon(request)
        );

    }

    // =====================================================
    // ADMIN
    // Update Coupon
    // =====================================================

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CouponResponse> updateCoupon(
            @PathVariable Long id,
            @Valid @RequestBody CouponRequest request
    ) {

        return ResponseEntity.ok(
                couponService.updateCoupon(id, request)
        );

    }

    // =====================================================
    // ADMIN
    // Delete Coupon
    // =====================================================

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCoupon(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                couponService.deleteCoupon(id)
        );

    }

    // =====================================================
    // ADMIN
    // Get All Coupons
    // =====================================================

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<CouponResponse>> getAllCoupons() {

        return ResponseEntity.ok(
                couponService.getAllCoupons()
        );

    }

    // =====================================================
    // USER
    // Get Active Coupons
    // =====================================================

    @GetMapping("/active")
    public ResponseEntity<List<CouponResponse>> getActiveCoupons() {

        return ResponseEntity.ok(
                couponService.getActiveCoupons()
        );

    }

    // =====================================================
    // USER
    // Validate Coupon
    // =====================================================

    @GetMapping("/validate/{code}")
    public ResponseEntity<CouponResponse> validateCoupon(
            @PathVariable String code
    ) {

        return ResponseEntity.ok(
                couponService.validateCoupon(code)
        );

    }

    // =====================================================
    // USER
    // Apply Coupon
    // =====================================================

    @PostMapping("/apply")
    public ResponseEntity<BigDecimal> applyCoupon(

            @RequestParam String code,

            @RequestParam BigDecimal amount

    ) {

        return ResponseEntity.ok(

                couponService.applyCoupon(
                        code,
                        amount
                )

        );

    }

}