package com.ecommerce.repository;

import com.ecommerce.entity.Order;
import com.ecommerce.entity.Payment;
import com.ecommerce.entity.PaymentStatus;
import com.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository
        extends JpaRepository<Payment, Long> {

    List<Payment> findByUserOrderByCreatedAtDesc(User user);

    Optional<Payment> findByOrder(Order order);

    Optional<Payment> findByRazorpayPaymentId(String paymentId);

    List<Payment> findByPaymentStatus(PaymentStatus status);

}