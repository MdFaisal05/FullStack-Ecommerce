package com.ecommerce.repository;

import com.ecommerce.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    // ============================================
    // Find Coupon By Code
    // ============================================

    Optional<Coupon> findByCode(String code);

    // ============================================
    // Active Coupons
    // ============================================

    List<Coupon> findByActiveTrue();

    // ============================================
    // Active Coupon By Code
    // ============================================

    Optional<Coupon> findByCodeAndActiveTrue(String code);

    // ============================================
    // Expired Coupons
    // ============================================

    List<Coupon> findByExpiryDateBefore(
            LocalDateTime dateTime
    );

    // ============================================
    // Valid Coupons
    // ============================================

    List<Coupon> findByExpiryDateAfter(
            LocalDateTime dateTime
    );

    // ============================================
    // Active & Valid Coupons
    // ============================================

    List<Coupon> findByActiveTrueAndExpiryDateAfter(
            LocalDateTime dateTime
    );

    // ============================================
    // Count Active Coupons
    // ============================================

    long countByActiveTrue();

}