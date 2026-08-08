package com.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ===================================
    // User
    // ===================================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // ===================================
    // Order Items
    // ===================================

    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Builder.Default
    private List<OrderItem> orderItems = new ArrayList<>();

    // ===================================
    // Shipping Details
    // ===================================

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String mobile;

    @Column(nullable = false, length = 500)
    private String address;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String pincode;

    @Column(nullable = false)
    private String country;

    // ===================================
    // Order Information
    // ===================================

    @Column(nullable = false)
    private Integer totalItems;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal totalAmount;

    // ===================================
    // Order Status
    // ===================================

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus orderStatus;

    // ===================================
    // Payment
    // ===================================

    @Column(nullable = false)
    private Boolean paymentDone;

    @Column(length = 100)
    private String paymentMethod;

    @Column(length = 100)
    private String paymentId;

    // ===================================
    // Dates
    // ===================================

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // ===================================
    // Auto Date
    // ===================================

    @PrePersist
    public void prePersist() {

        createdAt = LocalDateTime.now();

        updatedAt = LocalDateTime.now();

        if (orderStatus == null) {
            orderStatus = OrderStatus.PENDING;
        }

        if (paymentDone == null) {
            paymentDone = false;
        }

    }

    @PreUpdate
    public void preUpdate() {

        updatedAt = LocalDateTime.now();

    }

}