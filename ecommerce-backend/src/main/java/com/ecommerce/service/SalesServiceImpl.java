package com.ecommerce.service;

import com.ecommerce.dto.SalesResponse;
import com.ecommerce.entity.OrderStatus;
import com.ecommerce.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SalesServiceImpl implements SalesService {

    private final OrderRepository orderRepository;

    @Override
    public SalesResponse getSalesReport() {

        return SalesResponse.builder()

                .totalOrders(orderRepository.count())

                .totalSales(
                        orderRepository.getTotalRevenue() == null
                                ? 0.0
                                : orderRepository.getTotalRevenue().doubleValue()
                )

                .totalDiscount(0.0)

                .totalRevenue(
                        orderRepository.getTotalRevenue() == null
                                ? 0.0
                                : orderRepository.getTotalRevenue().doubleValue()
                )

                .pendingOrders(
                        orderRepository.countByOrderStatus(OrderStatus.PENDING)
                )

                .deliveredOrders(
                        orderRepository.countByOrderStatus(OrderStatus.DELIVERED)
                )

                .cancelledOrders(
                        orderRepository.countByOrderStatus(OrderStatus.CANCELLED)
                )

                .build();
    }
}