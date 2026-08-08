package com.ecommerce.service;

import java.util.Map;

public interface ReportService {

    Map<String, Object> getTodayReport();

    Map<String, Object> getMonthlyReport();

    Map<String, Object> getYearlyReport();

    Map<String, Object> getRecentOrders();

    Map<String, Object> getLowStockProducts();

}