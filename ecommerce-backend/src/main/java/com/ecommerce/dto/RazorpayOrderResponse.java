package com.ecommerce.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RazorpayOrderResponse {

    private String orderId;

    private Object amount;

    private Object currency;

    private String razorpayKey;

}