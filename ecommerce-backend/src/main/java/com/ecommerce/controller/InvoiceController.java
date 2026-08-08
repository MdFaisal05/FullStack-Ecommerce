package com.ecommerce.controller;

import com.ecommerce.service.PDFService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invoice")
@RequiredArgsConstructor
public class InvoiceController {

    private final PDFService pdfService;

    @GetMapping("/{orderId}")
    public ResponseEntity<byte[]> downloadInvoice(
            @PathVariable Long orderId) {

        byte[] pdf = pdfService.generateInvoice(orderId);

        return ResponseEntity.ok()

                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=Invoice_" + orderId + ".pdf"
                )

                .contentType(MediaType.APPLICATION_PDF)

                .body(pdf);
    }
}