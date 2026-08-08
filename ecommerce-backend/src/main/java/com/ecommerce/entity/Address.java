package com.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "addresses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ===============================
    // User
    // ===============================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // ===============================
    // Customer Details
    // ===============================

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, length = 15)
    private String mobile;

    @Column(nullable = false)
    private String addressLine;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false, length = 10)
    private String pincode;

    // HOME / OFFICE / OTHER
    @Column(nullable = false)
    private String addressType;

    @Builder.Default
    private Boolean defaultAddress = false;
}