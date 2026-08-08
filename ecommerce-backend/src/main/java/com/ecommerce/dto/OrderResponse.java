package com.ecommerce.dto;

import com.ecommerce.entity.OrderStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {

    private Long orderId;

    private OrderStatus orderStatus;

    private Integer totalItems;

    private BigDecimal totalAmount;

    // Shipping Address

    private String fullName;

    private String mobile;

    private String address;

    private String city;

    private String state;

    private String pincode;

    private String country;

    // Dates

    private LocalDateTime createdAt;

    // Ordered Products

    private List<OrderItemResponse> items;

}