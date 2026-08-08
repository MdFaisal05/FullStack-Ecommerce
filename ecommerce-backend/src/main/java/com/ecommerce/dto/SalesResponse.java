package com.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalesResponse {

    private Long totalOrders;

    private Double totalSales;

    private Double totalDiscount;

    private Double totalRevenue;

    private Long pendingOrders;

    private Long deliveredOrders;

    private Long cancelledOrders;
}