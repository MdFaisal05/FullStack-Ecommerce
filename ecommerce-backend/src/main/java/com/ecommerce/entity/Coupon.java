package com.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "coupons")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Coupon Code
    @Column(nullable = false, unique = true, length = 50)
    private String code;

    // Flat or Percentage Discount
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal discount;

    // true = Percentage
    // false = Flat
    @Column(nullable = false)
    private Boolean percentage;

    // Minimum Order Amount
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal minimumOrderAmount;

    // Maximum Discount (Only Percentage Coupon)
    @Column(precision = 10, scale = 2)
    private BigDecimal maximumDiscount;

    // Expiry Date
    @Column(nullable = false)
    private LocalDateTime expiryDate;

    // Usage Limit
    @Column(nullable = false)
    private Integer usageLimit;

    // Used Count
    @Column(nullable = false)
    private Integer usedCount;

    // Active / Inactive
    @Column(nullable = false)
    private Boolean active;

    @Column(nullable =false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {

        createdAt = LocalDateTime.now();

        updatedAt = LocalDateTime.now();

        if (active == null) {
            active = true;
        }

        if (usedCount == null) {
            usedCount = 0;
        }

    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

}