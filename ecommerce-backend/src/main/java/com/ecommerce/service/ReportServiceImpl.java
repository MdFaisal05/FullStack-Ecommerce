package com.ecommerce.service;

import com.ecommerce.entity.Order;
import com.ecommerce.entity.Product;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    // ==========================================
    // Today Report
    // ==========================================

    @Override
    public Map<String, Object> getTodayReport() {

        LocalDate today = LocalDate.now();

        LocalDateTime start = today.atStartOfDay();

        LocalDateTime end = today.plusDays(1).atStartOfDay();

        List<Order> orders =
                orderRepository.findByCreatedAtBetween(start, end);

        BigDecimal sales = orders.stream()

                .map(Order::getTotalAmount)

                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<String, Object> report = new HashMap<>();

        report.put("date", today);

        report.put("orders", orders.size());

        report.put("sales", sales);

        return report;

    }

    // ==========================================
    // Monthly Report
    // ==========================================

    @Override
    public Map<String, Object> getMonthlyReport() {

        LocalDate today = LocalDate.now();

        LocalDateTime start = today.withDayOfMonth(1).atStartOfDay();

        LocalDateTime end = start.plusMonths(1);

        List<Order> orders =
                orderRepository.findByCreatedAtBetween(start, end);

        BigDecimal sales = orders.stream()

                .map(Order::getTotalAmount)

                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<String, Object> report = new HashMap<>();

        report.put("month", today.getMonth());

        report.put("orders", orders.size());

        report.put("sales", sales);

        return report;

    }

    // ==========================================
    // Yearly Report
    // ==========================================

    @Override
    public Map<String, Object> getYearlyReport() {

        LocalDate today = LocalDate.now();

        LocalDateTime start =
                today.withDayOfYear(1).atStartOfDay();

        LocalDateTime end = start.plusYears(1);

        List<Order> orders =
                orderRepository.findByCreatedAtBetween(start, end);

        BigDecimal sales = orders.stream()

                .map(Order::getTotalAmount)

                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<String, Object> report = new HashMap<>();

        report.put("year", today.getYear());

        report.put("orders", orders.size());

        report.put("sales", sales);

        return report;

    }

    // ==========================================
    // Recent Orders
    // ==========================================

    @Override
    public Map<String, Object> getRecentOrders() {

        Map<String, Object> report = new HashMap<>();

        report.put(
                "orders",
                orderRepository.findTop10ByOrderByCreatedAtDesc()
        );

        return report;

    }

    // ==========================================
    // Low Stock Products
    // ==========================================

    @Override
    public Map<String, Object> getLowStockProducts() {

        List<Product> products =
                productRepository.findByStockLessThanEqual(5);

        Map<String, Object> report = new HashMap<>();

        report.put("count", products.size());

        report.put("products", products);

        return report;

    }

}