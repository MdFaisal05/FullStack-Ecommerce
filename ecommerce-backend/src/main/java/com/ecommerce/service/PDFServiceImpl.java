package com.ecommerce.service;

import com.ecommerce.entity.Order;
import com.ecommerce.entity.OrderItem;
import com.ecommerce.repository.OrderRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PDFServiceImpl implements PDFService {

    private final OrderRepository orderRepository;

    @Override
    public byte[] generateInvoice(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException("Order Not Found"));

        try {

            ByteArrayOutputStream outputStream =
                    new ByteArrayOutputStream();

            Document document = new Document(PageSize.A4);

            PdfWriter.getInstance(document, outputStream);

            document.open();

            Font titleFont = new Font(
                    Font.FontFamily.HELVETICA,
                    22,
                    Font.BOLD
            );

            Paragraph title = new Paragraph(
                    "LazyMarket Invoice",
                    titleFont
            );

            title.setAlignment(Element.ALIGN_CENTER);

            document.add(title);

            document.add(new Paragraph(" "));

            // ===========================
            // Order Information
            // ===========================

            document.add(new Paragraph(
                    "Order ID : " + order.getId()));

            document.add(new Paragraph(
                    "Customer : "
                            + order.getUser().getFirstName()
                            + " "
                            + order.getUser().getLastName()));

            document.add(new Paragraph(
                    "Email : "
                            + order.getUser().getEmail()));

            document.add(new Paragraph(
                    "Order Date : "
                            + order.getCreatedAt()));

            document.add(new Paragraph(" "));

            // ===========================
            // Shipping Address
            // ===========================

            document.add(new Paragraph("Shipping Address"));

            document.add(new Paragraph(
                    order.getFullName()));

            document.add(new Paragraph(
                    order.getAddress()));

            document.add(new Paragraph(
                    order.getCity()
                            + ", "
                            + order.getState()));

            document.add(new Paragraph(
                    order.getPincode()
                            + ", "
                            + order.getCountry()));

            document.add(new Paragraph(
                    "Mobile : "
                            + order.getMobile()));

            document.add(new Paragraph(" "));

            // ===========================
            // Products Table
            // ===========================

            PdfPTable table = new PdfPTable(4);

            table.setWidthPercentage(100);

            table.setWidths(new float[]{4, 2, 2, 2});

            table.addCell("Product");
            table.addCell("Qty");
            table.addCell("Price");
            table.addCell("Subtotal");

            for (OrderItem item : order.getOrderItems()) {

                table.addCell(item.getProductName());

                table.addCell(
                        String.valueOf(item.getQuantity()));

                table.addCell(
                        "₹ " + item.getPrice());

                BigDecimal subtotal =
                        item.getSubtotal() != null
                                ? item.getSubtotal()
                                : item.getPrice().multiply(
                                BigDecimal.valueOf(
                                        item.getQuantity()));

                table.addCell("₹ " + subtotal);

            }

            document.add(table);

            document.add(new Paragraph(" "));

            // ===========================
            // Total
            // ===========================

            Font totalFont = new Font(
                    Font.FontFamily.HELVETICA,
                    14,
                    Font.BOLD
            );

            Paragraph total = new Paragraph(
                    "Total Amount : ₹ "
                            + order.getTotalAmount(),
                    totalFont
            );

            total.setAlignment(Element.ALIGN_RIGHT);

            document.add(total);

            document.add(new Paragraph(" "));

            // ===========================
            // Order Status
            // ===========================

            document.add(new Paragraph(
                    "Order Status : "
                            + order.getOrderStatus()));

            document.add(new Paragraph(
                    "Payment Method : "
                            + order.getPaymentMethod()));

            document.add(new Paragraph(
                    "Payment Status : "
                            + (order.getPaymentDone()
                            ? "PAID"
                            : "PENDING")));

            document.add(new Paragraph(" "));

            Paragraph thanks = new Paragraph(
                    "Thank You For Shopping With LazyMarket!"
            );

            thanks.setAlignment(Element.ALIGN_CENTER);

            document.add(thanks);

            document.close();

            return outputStream.toByteArray();

        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed To Generate Invoice PDF",
                    e
            );

        }

    }

}