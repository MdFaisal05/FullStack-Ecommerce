package com.ecommerce.service;

import com.ecommerce.dto.OrderItemResponse;
import com.ecommerce.dto.OrderResponse;
import com.ecommerce.dto.PlaceOrderRequest;
import com.ecommerce.dto.ShippingAddressRequest;

import com.ecommerce.entity.Cart;
import com.ecommerce.entity.CartItem;
import com.ecommerce.entity.Order;
import com.ecommerce.entity.OrderItem;
import com.ecommerce.entity.OrderStatus;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.Role;
import com.ecommerce.entity.User;

import com.ecommerce.exception.ResourceNotFoundException;

import com.ecommerce.repository.CartRepository;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ecommerce.dto.OrderTrackingResponse;

import com.ecommerce.service.EmailService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final CartRepository cartRepository;

    private final ProductRepository productRepository;

    private final UserRepository userRepository;

    private final EmailService emailService;


    // ===========================================================
    // Logged In User
    // ===========================================================

    private User getLoggedInUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User Not Found"));

    }

    // ===========================================================
    // OrderItem -> DTO
    // ===========================================================

    private OrderItemResponse mapOrderItem(OrderItem item) {

        return OrderItemResponse.builder()

                .productId(item.getProduct().getId())

                .productName(item.getProductName())

                .imageUrl(item.getImageUrl())

                .price(item.getPrice())

                .quantity(item.getQuantity())

                .subtotal(item.getSubtotal())

                .build();

    }

    // ===========================================================
    // Order -> DTO
    // ===========================================================

    private OrderResponse mapOrder(Order order) {

        List<OrderItemResponse> items =

                order.getOrderItems()
                        .stream()
                        .map(this::mapOrderItem)
                        .toList();

        return OrderResponse.builder()

                .orderId(order.getId())

                .orderStatus(order.getOrderStatus())

                .totalItems(order.getTotalItems())

                .totalAmount(order.getTotalAmount())

                // Shipping Details

                .fullName(order.getFullName())

                .mobile(order.getMobile())

                .address(order.getAddress())

                .city(order.getCity())

                .state(order.getState())

                .pincode(order.getPincode())

                .country(order.getCountry())

                // Date

                .createdAt(order.getCreatedAt())

                // Items

                .items(items)

                .build();

    }

    @Override
    public OrderResponse placeOrder(PlaceOrderRequest request) {

        // ============================================
        // Get Logged In User
        // ============================================

        User user = getLoggedInUser();

        // ============================================
        // Get User Cart
        // ============================================

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart Not Found"));

        // ============================================
        // Check Empty Cart
        // ============================================

        if (cart.getCartItems() == null || cart.getCartItems().isEmpty()) {

            throw new RuntimeException("Cart Is Empty");

        }

        // ============================================
        // Shipping Address
        // ============================================

        ShippingAddressRequest address =
                request.getShippingAddress();

        // ============================================
        // Create Order
        // ============================================

        Order order = Order.builder()

                .user(user)

                .fullName(address.getFullName())

                .mobile(address.getMobile())

                .address(address.getAddress())

                .city(address.getCity())

                .state(address.getState())

                .pincode(address.getPincode())

                .country(address.getCountry())

                .totalItems(cart.getCartItems().size())

                .totalAmount(cart.getTotalAmount())

                .paymentDone(false)

                .paymentMethod("COD")

                .orderStatus(OrderStatus.PENDING)

                .build();

        // Remaining Logic

        // ============================================
        // Create Order Items
        // ============================================

        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cart.getCartItems()) {

            Product product = cartItem.getProduct();

            // ==============================
            // Stock Validation
            // ==============================

            if (product.getStock() < cartItem.getQuantity()) {

                throw new RuntimeException(
                        product.getName() + " is Out Of Stock"
                );

            }

            // ==============================
            // Reduce Product Stock
            // ==============================

            product.setStock(
                    product.getStock() - cartItem.getQuantity()
            );

            productRepository.save(product);

            // ==============================
            // Create Order Item
            // ==============================

            OrderItem orderItem = OrderItem.builder()

                    .order(order)

                    .product(product)

                    .productName(product.getName())

                    .imageUrl(product.getImageUrl())

                    .price(cartItem.getPrice())

                    .quantity(cartItem.getQuantity())

                    .subtotal(cartItem.getSubtotal())

                    .build();

            orderItems.add(orderItem);

        }

        // ============================================
        // Set Order Items
        // ============================================

        order.setOrderItems(orderItems);

        for (OrderItem item : orderItems) {
            item.setOrder(order);
        }

        // ============================================
        // Save Order
        // ============================================

        Order savedOrder = orderRepository.save(order);

        emailService.sendOrderPlacedEmail(

                user.getEmail(),

                savedOrder.getId()

        );

        // ============================================
        // Clear Cart
        // ============================================

        cart.getCartItems().clear();

        cart.setTotalAmount(BigDecimal.ZERO);

        cartRepository.save(cart);

        // ============================================
        // Return Response
        // ============================================

        return mapOrder(savedOrder);


    }

    @Override
    public List<OrderResponse> getMyOrders() {

        User user = getLoggedInUser();

        return orderRepository
                .findByUserOrderByCreatedAtDesc(user)
                .stream()
                .map(this::mapOrder)
                .toList();

    }

    @Override
    public OrderResponse getOrderById(Long orderId) {

        User user = getLoggedInUser();

        Order order = orderRepository.findById(orderId)

                .orElseThrow(() ->

                        new ResourceNotFoundException("Order Not Found"));

        // ==========================================
        // Security Check
        // ==========================================

        if (!order.getUser().getId().equals(user.getId())
                && user.getRole() != Role.ROLE_ADMIN) {

            throw new RuntimeException(
                    "You Are Not Authorized To View This Order"
            );

        }

        return mapOrder(order);

    }

    @Override
    public List<OrderResponse> getAllOrders() {

        return orderRepository.findAll()

                .stream()

                .map(this::mapOrder)

                .toList();


    }

    // ===========================================================
// Cancel Order
// ===========================================================

    @Override
    public String cancelOrder(Long orderId) {

        User user = getLoggedInUser();

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order Not Found"));

        if (!order.getUser().getId().equals(user.getId())
                && user.getRole() != Role.ROLE_ADMIN) {

            throw new RuntimeException(
                    "You Are Not Authorized To Cancel This Order"
            );

        }

        if (order.getOrderStatus() == OrderStatus.DELIVERED) {

            throw new RuntimeException(
                    "Delivered Order Cannot Be Cancelled"
            );

        }

        if (order.getOrderStatus() == OrderStatus.CANCELLED) {

            throw new RuntimeException(
                    "Order Is Already Cancelled"
            );

        }

        // Restore Stock

        for (OrderItem item : order.getOrderItems()) {

            Product product = item.getProduct();

            product.setStock(
                    product.getStock() + item.getQuantity()
            );

            productRepository.save(product);

        }

        order.setOrderStatus(OrderStatus.CANCELLED);

        orderRepository.save(order);

        emailService.sendOrderCancelledEmail(

                user.getEmail(),

                order.getId()

        );

        return "Order Cancelled Successfully";

    }

// ===========================================================
// Update Order Status (Admin)
// ===========================================================

    @Override
    public OrderResponse updateOrderStatus(
            Long orderId,
            OrderStatus orderStatus
    ) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order Not Found"));

        order.setOrderStatus(orderStatus);

        Order updatedOrder = orderRepository.save(order);

        return mapOrder(updatedOrder);

    }

    @Override
    public OrderTrackingResponse trackOrder(Long orderId) {

        User user = getLoggedInUser();

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order Not Found"));

        if (!order.getUser().getId().equals(user.getId())
                && user.getRole() != Role.ROLE_ADMIN) {

            throw new RuntimeException(
                    "You Are Not Authorized To View This Order"
            );

        }

        return OrderTrackingResponse.builder()

                .orderId(order.getId())

                .status(order.getOrderStatus())

                .orderDate(order.getCreatedAt())

                .lastUpdated(order.getUpdatedAt())

                .build();

    }
}
