package com.ecommerce.service;

import com.ecommerce.dto.AnalyticsResponse;
import com.ecommerce.entity.OrderStatus;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AnalyticsServiceImpl implements AnalyticsService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    @Override
    public AnalyticsResponse getAnalytics() {

        BigDecimal revenue = orderRepository.getTotalRevenue();

        return AnalyticsResponse.builder()
                .totalUsers(userRepository.count())
                .totalProducts(productRepository.count())
                .totalOrders(orderRepository.count())
                .totalRevenue(
                        revenue == null
                                ? 0.0
                                : revenue.doubleValue()
                )
                .pendingOrders(orderRepository.countByOrderStatus(OrderStatus.PENDING))
                .deliveredOrders(orderRepository.countByOrderStatus(OrderStatus.DELIVERED))
                .cancelledOrders(orderRepository.countByOrderStatus(OrderStatus.CANCELLED))
                .lowStockProducts(productRepository.countByStockLessThanEqual(5))
                .build();
    }
}