package com.ecommerce.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CouponResponse {

    // ============================================
    // Coupon Id
    // ============================================

    private Long id;

    // ============================================
    // Coupon Code
    // ============================================

    private String code;

    // ============================================
    // Discount
    // ============================================

    private BigDecimal discount;

    // ============================================
    // Coupon Type
    // true = Percentage
    // false = Flat
    // ============================================

    private Boolean percentage;

    // ============================================
    // Minimum Order Amount
    // ============================================

    private BigDecimal minimumOrderAmount;

    // ============================================
    // Maximum Discount
    // ============================================

    private BigDecimal maximumDiscount;

    // ============================================
    // Expiry Date
    // ============================================

    private LocalDateTime expiryDate;

    // ============================================
    // Usage Details
    // ============================================

    private Integer usageLimit;

    private Integer usedCount;

    // ============================================
    // Status
    // ============================================

    private Boolean active;

    // ============================================
    // Dates
    // ============================================

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}