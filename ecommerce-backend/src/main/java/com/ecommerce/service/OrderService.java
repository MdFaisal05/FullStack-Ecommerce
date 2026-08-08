package com.ecommerce.service;

import com.ecommerce.dto.OrderResponse;
import com.ecommerce.dto.PlaceOrderRequest;
import com.ecommerce.entity.OrderStatus;
import com.ecommerce.dto.OrderTrackingResponse;

import java.util.List;

public interface OrderService {

    // ============================================
    // USER APIs
    // ============================================

    /**
     * Place a new order from the current user's cart.
     */
    OrderResponse placeOrder(PlaceOrderRequest request);

    /**
     * Get all orders of the logged-in user.
     */
    List<OrderResponse> getMyOrders();

    OrderTrackingResponse trackOrder(Long orderId);

    /**
     * Get a specific order by ID.
     */
    OrderResponse getOrderById(Long orderId);

    /**
     * Cancel an order.
     */
    String cancelOrder(Long orderId);

    // ============================================
    // ADMIN APIs
    // ============================================

    /**
     * Get all orders.
     */
    List<OrderResponse> getAllOrders();

    /**
     * Update order status.
     */
    OrderResponse updateOrderStatus(
            Long orderId,
            OrderStatus orderStatus
    );

}