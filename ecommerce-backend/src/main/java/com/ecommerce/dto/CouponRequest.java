package com.ecommerce.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CouponRequest {

    // ============================================
    // Coupon Code
    // ============================================

    @NotBlank(message = "Coupon Code Is Required")
    private String code;

    // ============================================
    // Discount Amount / Percentage
    // ============================================

    @NotNull(message = "Discount Is Required")
    @DecimalMin(value = "0.0", inclusive = false,
            message = "Discount Must Be Greater Than 0")
    private BigDecimal discount;

    // ============================================
    // true = Percentage
    // false = Flat Discount
    // ============================================

    @NotNull(message = "Coupon Type Is Required")
    private Boolean percentage;

    // ============================================
    // Minimum Order Amount
    // ============================================

    @NotNull(message = "Minimum Order Amount Is Required")
    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal minimumOrderAmount;

    // ============================================
    // Maximum Discount
    // (Only For Percentage Coupon)
    // ============================================

    private BigDecimal maximumDiscount;

    // ============================================
    // Expiry Date
    // ============================================

    @NotNull(message = "Expiry Date Is Required")
    @Future(message = "Expiry Date Must Be In Future")
    private LocalDateTime expiryDate;

    // ============================================
    // Usage Limit
    // ============================================

    @NotNull(message = "Usage Limit Is Required")
    @Positive(message = "Usage Limit Must Be Greater Than 0")
    private Integer usageLimit;

    // ============================================
    // Active
    // ============================================

    private Boolean active;

}