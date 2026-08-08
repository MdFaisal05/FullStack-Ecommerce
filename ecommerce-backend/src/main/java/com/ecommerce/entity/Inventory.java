package com.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "inventory")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Product
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // Previous Stock
    @Column(nullable = false)
    private Integer previousStock;

    // Quantity Changed
    @Column(nullable = false)
    private Integer quantityChanged;

    // New Stock
    @Column(nullable = false)
    private Integer newStock;

    // STOCK_IN / STOCK_OUT / ADJUSTMENT
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InventoryOperation operation;

    // Optional Remarks
    @Column(length = 500)
    private String remarks;

    // Admin Name / Email
    @Column(nullable = false)
    private String updatedBy;

    // Time
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        updatedAt = LocalDateTime.now();
    }

}