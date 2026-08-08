package com.ecommerce.repository;

import com.ecommerce.entity.Order;
import com.ecommerce.entity.OrderStatus;
import com.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // ============================================
    // Logged In User Orders
    // ============================================

    List<Order> findByUserOrderByCreatedAtDesc(User user);

    // ============================================
    // Orders By Status
    // ============================================

    List<Order> findByOrderStatus(OrderStatus orderStatus);

    // ============================================
    // Latest Orders
    // ============================================

    List<Order> findTop10ByOrderByCreatedAtDesc();

    // ============================================
    // Orders Between Dates
    // ============================================

    List<Order> findByCreatedAtBetween(
            LocalDateTime startDate,
            LocalDateTime endDate
    );

    // ============================================
    // Count Orders By Status
    // ============================================

    long countByOrderStatus(OrderStatus orderStatus);

    // ============================================
    // Count User Orders
    // ============================================

    long countByUser(User user);

    // ============================================
    // Dashboard Total Revenue
    // ============================================

    @Query("""
        SELECT COALESCE(SUM(o.totalAmount),0)
        FROM Order o
        WHERE o.orderStatus = com.ecommerce.entity.OrderStatus.DELIVERED
        """)
    BigDecimal getTotalRevenue();

}