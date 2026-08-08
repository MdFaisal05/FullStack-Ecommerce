package com.ecommerce.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressResponse {

    private Long id;

    private String fullName;

    private String mobile;

    private String addressLine;

    private String city;

    private String state;

    private String country;

    private String pincode;

    private String addressType;

    private Boolean defaultAddress;

}