package com.ecommerce.service;

import com.ecommerce.dto.CouponRequest;
import com.ecommerce.dto.CouponResponse;

import java.math.BigDecimal;
import java.util.List;

public interface CouponService {

    // =====================================================
    // ADMIN
    // Create Coupon
    // =====================================================

    CouponResponse createCoupon(
            CouponRequest request
    );

    // =====================================================
    // ADMIN
    // Update Coupon
    // =====================================================

    CouponResponse updateCoupon(
            Long couponId,
            CouponRequest request
    );

    // =====================================================
    // ADMIN
    // Delete Coupon
    // =====================================================

    String deleteCoupon(
            Long couponId
    );

    // =====================================================
    // ADMIN
    // Get All Coupons
    // =====================================================

    List<CouponResponse> getAllCoupons();

    // =====================================================
    // USER
    // Get Active Coupons
    // =====================================================

    List<CouponResponse> getActiveCoupons();

    // =====================================================
    // USER
    // Validate Coupon
    // =====================================================

    CouponResponse validateCoupon(
            String code
    );

    // =====================================================
    // USER
    // Calculate Discount
    // =====================================================

    BigDecimal calculateDiscount(
            String code,
            BigDecimal orderAmount
    );

    // =====================================================
    // USER
    // Apply Coupon
    // =====================================================

    BigDecimal applyCoupon(
            String code,
            BigDecimal totalAmount
    );

    // =====================================================
    // USER
    // Increase Coupon Usage
    // =====================================================

    void increaseCouponUsage(
            String code
    );

}