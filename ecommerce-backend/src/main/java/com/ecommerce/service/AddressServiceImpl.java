package com.ecommerce.service;

import com.ecommerce.dto.AddressRequest;
import com.ecommerce.dto.AddressResponse;
import com.ecommerce.entity.Address;
import com.ecommerce.entity.User;
import com.ecommerce.repository.AddressRepository;
import com.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    // ==========================================
    // Get Logged User
    // ==========================================

    private User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User Not Found"));

    }

    // ==========================================
    // Entity -> DTO
    // ==========================================

    private AddressResponse map(Address address) {

        return AddressResponse.builder()

                .id(address.getId())

                .fullName(address.getFullName())

                .mobile(address.getMobile())

                .addressLine(address.getAddressLine())

                .city(address.getCity())

                .state(address.getState())

                .country(address.getCountry())

                .pincode(address.getPincode())

                .addressType(address.getAddressType())

                .defaultAddress(address.getDefaultAddress())

                .build();

    }

    // ==========================================
    // Add Address
    // ==========================================

    @Override
    public AddressResponse addAddress(
            AddressRequest request
    ) {

        User user = getCurrentUser();

        Address address = Address.builder()

                .user(user)

                .fullName(request.getFullName())

                .mobile(request.getMobile())

                .addressLine(request.getAddressLine())

                .city(request.getCity())

                .state(request.getState())

                .country(request.getCountry())

                .pincode(request.getPincode())

                .addressType(request.getAddressType())

                .defaultAddress(false)

                .build();

        Address saved =
                addressRepository.save(address);

        return map(saved);

    }

    // ==========================================
    // Update Address
    // ==========================================

    @Override
    public AddressResponse updateAddress(
            Long addressId,
            AddressRequest request
    ) {

        User user = getCurrentUser();

        Address address =
                addressRepository
                        .findByIdAndUser(addressId, user)
                        .orElseThrow(() ->
                                new RuntimeException("Address Not Found"));

        address.setFullName(request.getFullName());

        address.setMobile(request.getMobile());

        address.setAddressLine(request.getAddressLine());

        address.setCity(request.getCity());

        address.setState(request.getState());

        address.setCountry(request.getCountry());

        address.setPincode(request.getPincode());

        address.setAddressType(request.getAddressType());

        Address updated =
                addressRepository.save(address);

        return map(updated);

    }

    // ==========================================
    // Delete Address
    // ==========================================

    @Override
    public String deleteAddress(
            Long addressId
    ) {

        User user = getCurrentUser();

        Address address =
                addressRepository
                        .findByIdAndUser(addressId, user)
                        .orElseThrow(() ->
                                new RuntimeException("Address Not Found"));

        addressRepository.delete(address);

        return "Address Deleted Successfully";

    }

    // ==========================================
    // My Addresses
    // ==========================================

    @Override
    public List<AddressResponse> getMyAddresses() {

        User user = getCurrentUser();

        return addressRepository
                .findByUser(user)
                .stream()
                .map(this::map)
                .toList();

    }

    // ==========================================
    // Get Address
    // ==========================================

    @Override
    public AddressResponse getAddress(
            Long addressId
    ) {

        User user = getCurrentUser();

        Address address =
                addressRepository
                        .findByIdAndUser(addressId, user)
                        .orElseThrow(() ->
                                new RuntimeException("Address Not Found"));

        return map(address);

    }

    // ==========================================
    // Set Default Address
    // ==========================================

    @Override
    public String setDefaultAddress(
            Long addressId
    ) {

        User user = getCurrentUser();

        addressRepository
                .findByUserAndDefaultAddressTrue(user)
                .ifPresent(address -> {

                    address.setDefaultAddress(false);

                    addressRepository.save(address);

                });

        Address address =
                addressRepository
                        .findByIdAndUser(addressId, user)
                        .orElseThrow(() ->
                                new RuntimeException("Address Not Found"));

        address.setDefaultAddress(true);

        addressRepository.save(address);

        return "Default Address Updated Successfully";

    }

}