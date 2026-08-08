package com.ecommerce.dto;

import com.ecommerce.entity.OrderStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderTrackingResponse {

    private Long orderId;

    private OrderStatus status;

    private LocalDateTime orderDate;

    private LocalDateTime lastUpdated;

}