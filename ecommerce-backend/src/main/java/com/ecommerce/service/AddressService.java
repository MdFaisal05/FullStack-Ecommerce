package com.ecommerce.service;

import com.ecommerce.dto.AddressRequest;
import com.ecommerce.dto.AddressResponse;

import java.util.List;

public interface AddressService {

    // ============================
    // Add Address
    // ============================

    AddressResponse addAddress(
            AddressRequest request
    );

    // ============================
    // Update Address
    // ============================

    AddressResponse updateAddress(
            Long addressId,
            AddressRequest request
    );

    // ============================
    // Delete Address
    // ============================

    String deleteAddress(
            Long addressId
    );

    // ============================
    // Get Logged User Addresses
    // ============================

    List<AddressResponse> getMyAddresses();

    // ============================
    // Get Address By Id
    // ============================

    AddressResponse getAddress(
            Long addressId
    );

    // ============================
    // Set Default Address
    // ============================

    String setDefaultAddress(
            Long addressId
    );

}