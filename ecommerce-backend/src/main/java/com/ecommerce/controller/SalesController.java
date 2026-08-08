package com.ecommerce.controller;

import com.ecommerce.dto.SalesResponse;
import com.ecommerce.service.SalesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sales")
@RequiredArgsConstructor
public class SalesController {

    private final SalesService salesService;

    @GetMapping
    public ResponseEntity<SalesResponse> getSalesReport() {

        return ResponseEntity.ok(
                salesService.getSalesReport()
        );
    }
}