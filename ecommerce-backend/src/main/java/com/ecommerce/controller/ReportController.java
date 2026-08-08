package com.ecommerce.controller;

import com.ecommerce.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/today")
    public Object today() {
        return reportService.getTodayReport();
    }

    @GetMapping("/monthly")
    public Object monthly() {
        return reportService.getMonthlyReport();
    }

    @GetMapping("/yearly")
    public Object yearly() {
        return reportService.getYearlyReport();
    }

    @GetMapping("/recent-orders")
    public Object recentOrders() {
        return reportService.getRecentOrders();
    }

    @GetMapping("/low-stock")
    public Object lowStock() {
        return reportService.getLowStockProducts();
    }

}