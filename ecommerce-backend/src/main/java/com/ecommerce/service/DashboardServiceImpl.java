package com.ecommerce.service;

import com.ecommerce.dto.DashboardResponse;
import com.ecommerce.entity.OrderStatus;
import com.ecommerce.repository.CategoryRepository;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final OrderRepository orderRepository;

    @Override
    public DashboardResponse getDashboard() {

        return DashboardResponse.builder()

                .totalUsers(userRepository.count())

                .totalProducts(productRepository.count())

                .totalCategories(categoryRepository.count())

                .totalOrders(orderRepository.count())

                .totalRevenue(orderRepository.getTotalRevenue())

                .pendingOrders(
                        orderRepository.countByOrderStatus(OrderStatus.PENDING)
                )

                .deliveredOrders(
                        orderRepository.countByOrderStatus(OrderStatus.DELIVERED)
                )

                .cancelledOrders(
                        orderRepository.countByOrderStatus(OrderStatus.CANCELLED)
                )

                .lowStockProducts(
                        productRepository.countByStockLessThanEqual(5)
                )

                .build();

    }

}