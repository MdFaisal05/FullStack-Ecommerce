package com.ecommerce.service;

public interface PDFService {

    byte[] generateInvoice(Long orderId);

}