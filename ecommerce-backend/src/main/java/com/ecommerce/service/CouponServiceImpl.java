package com.ecommerce.service;

import com.ecommerce.dto.CouponRequest;
import com.ecommerce.dto.CouponResponse;
import com.ecommerce.entity.Coupon;
import com.ecommerce.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;

    // =====================================================
    // Coupon -> Response DTO
    // =====================================================

    private CouponResponse mapCoupon(Coupon coupon) {

        return CouponResponse.builder()
                .id(coupon.getId())
                .code(coupon.getCode())
                .discount(coupon.getDiscount())
                .percentage(coupon.getPercentage())
                .minimumOrderAmount(coupon.getMinimumOrderAmount())
                .maximumDiscount(coupon.getMaximumDiscount())
                .expiryDate(coupon.getExpiryDate())
                .usageLimit(coupon.getUsageLimit())
                .usedCount(coupon.getUsedCount())
                .active(coupon.getActive())
                .createdAt(coupon.getCreatedAt())
                .updatedAt(coupon.getUpdatedAt())
                .build();

    }

    // =====================================================
    // Create Coupon
    // =====================================================

    @Override
    public CouponResponse createCoupon(
            CouponRequest request
    ) {

        if (couponRepository.findByCode(request.getCode()).isPresent()) {

            throw new RuntimeException(
                    "Coupon Already Exists"
            );

        }

        Coupon coupon = Coupon.builder()

                .code(request.getCode().trim().toUpperCase())

                .discount(request.getDiscount())

                .percentage(request.getPercentage())

                .minimumOrderAmount(
                        request.getMinimumOrderAmount()
                )

                .maximumDiscount(
                        request.getMaximumDiscount()
                )

                .expiryDate(
                        request.getExpiryDate()
                )

                .usageLimit(
                        request.getUsageLimit()
                )

                .usedCount(0)

                .active(
                        request.getActive() == null
                                ? true
                                : request.getActive()
                )

                .build();

        Coupon savedCoupon =
                couponRepository.save(coupon);

        return mapCoupon(savedCoupon);

    }

    // =====================================================
    // Update Coupon
    // =====================================================

    @Override
    public CouponResponse updateCoupon(
            Long couponId,
            CouponRequest request
    ) {

        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() ->
                        new RuntimeException("Coupon Not Found"));

        coupon.setCode(
                request.getCode().trim().toUpperCase()
        );

        coupon.setDiscount(
                request.getDiscount()
        );

        coupon.setPercentage(
                request.getPercentage()
        );

        coupon.setMinimumOrderAmount(
                request.getMinimumOrderAmount()
        );

        coupon.setMaximumDiscount(
                request.getMaximumDiscount()
        );

        coupon.setExpiryDate(
                request.getExpiryDate()
        );

        coupon.setUsageLimit(
                request.getUsageLimit()
        );

        coupon.setActive(
                request.getActive()
        );

        Coupon updatedCoupon =
                couponRepository.save(coupon);

        return mapCoupon(updatedCoupon);

    }

    // =====================================================
    // Delete Coupon
    // =====================================================

    @Override
    public String deleteCoupon(Long couponId) {

        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() ->
                        new RuntimeException("Coupon Not Found"));

        couponRepository.delete(coupon);

        return "Coupon Deleted Successfully";

    }

    // =====================================================
    // Get All Coupons
    // =====================================================

    @Override
    public List<CouponResponse> getAllCoupons() {

        return couponRepository.findAll()
                .stream()
                .map(this::mapCoupon)
                .toList();

    }

    // =====================================================
    // Get Active Coupons
    // =====================================================

    @Override
    public List<CouponResponse> getActiveCoupons() {

        return couponRepository
                .findByActiveTrue()
                .stream()
                .filter(coupon ->
                        coupon.getExpiryDate()
                                .isAfter(LocalDateTime.now()))
                .map(this::mapCoupon)
                .toList();

    }

    // =====================================================
    // Validate Coupon
    // =====================================================

    @Override
    public CouponResponse validateCoupon(String code) {

        Coupon coupon = couponRepository.findByCode(code.toUpperCase())
                .orElseThrow(() ->
                        new RuntimeException("Invalid Coupon"));

        if (!Boolean.TRUE.equals(coupon.getActive())) {
            throw new RuntimeException("Coupon Is Inactive");
        }

        if (coupon.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Coupon Has Expired");
        }

        if (coupon.getUsedCount() >= coupon.getUsageLimit()) {
            throw new RuntimeException("Coupon Usage Limit Exceeded");
        }

        return mapCoupon(coupon);

    }

    // =====================================================
    // Calculate Discount
    // =====================================================

    @Override
    public BigDecimal calculateDiscount(
            String code,
            BigDecimal totalAmount
    ) {

        Coupon coupon = couponRepository.findByCode(code.toUpperCase())
                .orElseThrow(() ->
                        new RuntimeException("Invalid Coupon"));

        if (!Boolean.TRUE.equals(coupon.getActive())) {
            throw new RuntimeException("Coupon Is Inactive");
        }

        if (coupon.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Coupon Has Expired");
        }

        if (coupon.getUsedCount() >= coupon.getUsageLimit()) {
            throw new RuntimeException("Coupon Usage Limit Exceeded");
        }

        if (totalAmount.compareTo(coupon.getMinimumOrderAmount()) < 0) {

            throw new RuntimeException(
                    "Minimum Order Amount Should Be "
                            + coupon.getMinimumOrderAmount()
            );

        }

        BigDecimal discount;

        if (Boolean.TRUE.equals(coupon.getPercentage())) {

            discount = totalAmount
                    .multiply(coupon.getDiscount())
                    .divide(BigDecimal.valueOf(100));

            if (coupon.getMaximumDiscount() != null
                    && discount.compareTo(coupon.getMaximumDiscount()) > 0) {

                discount = coupon.getMaximumDiscount();

            }

        } else {

            discount = coupon.getDiscount();

        }

        return discount;

    }

    // =====================================================
    // Apply Coupon
    // =====================================================

    @Override
    public BigDecimal applyCoupon(
            String code,
            BigDecimal totalAmount
    ) {

        BigDecimal discount =
                calculateDiscount(code, totalAmount);

        increaseCouponUsage(code);

        return totalAmount.subtract(discount);

    }

    // =====================================================
    // Increase Coupon Usage
    // =====================================================

    @Override
    public void increaseCouponUsage(String code) {

        Coupon coupon = couponRepository.findByCode(code.toUpperCase())
                .orElseThrow(() ->
                        new RuntimeException("Coupon Not Found"));

        coupon.setUsedCount(
                coupon.getUsedCount() + 1
        );

        couponRepository.save(coupon);

    }

}