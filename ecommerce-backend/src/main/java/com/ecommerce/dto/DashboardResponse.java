package com.ecommerce.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardResponse {

    private long totalUsers;

    private long totalProducts;

    private long totalCategories;

    private long totalOrders;

    private BigDecimal totalRevenue;

    private long pendingOrders;

    private long deliveredOrders;

    private long cancelledOrders;

    private long lowStockProducts;

}