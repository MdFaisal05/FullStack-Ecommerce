package com.ecommerce.controller;

import com.ecommerce.dto.AddressRequest;
import com.ecommerce.dto.AddressResponse;
import com.ecommerce.service.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/address")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    // ==========================================
    // Add Address
    // POST : /api/address
    // ==========================================

    @PostMapping
    public ResponseEntity<AddressResponse> addAddress(

            @Valid
            @RequestBody
            AddressRequest request

    ) {

        return ResponseEntity.ok(

                addressService.addAddress(request)

        );

    }

    // ==========================================
    // Get My Addresses
    // GET : /api/address
    // ==========================================

    @GetMapping
    public ResponseEntity<List<AddressResponse>> getMyAddresses() {

        return ResponseEntity.ok(

                addressService.getMyAddresses()

        );

    }

    // ==========================================
    // Get Address By Id
    // GET : /api/address/{id}
    // ==========================================

    @GetMapping("/{id}")
    public ResponseEntity<AddressResponse> getAddress(

            @PathVariable Long id

    ) {

        return ResponseEntity.ok(

                addressService.getAddress(id)

        );

    }

    // ==========================================
    // Update Address
    // PUT : /api/address/{id}
    // ==========================================

    @PutMapping("/{id}")
    public ResponseEntity<AddressResponse> updateAddress(

            @PathVariable Long id,

            @Valid
            @RequestBody
            AddressRequest request

    ) {

        return ResponseEntity.ok(

                addressService.updateAddress(

                        id,

                        request

                )

        );

    }

    // ==========================================
    // Delete Address
    // DELETE : /api/address/{id}
    // ==========================================

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAddress(

            @PathVariable Long id

    ) {

        return ResponseEntity.ok(

                addressService.deleteAddress(id)

        );

    }

    // ==========================================
    // Set Default Address
    // PUT : /api/address/default/{id}
    // ==========================================

    @PutMapping("/default/{id}")
    public ResponseEntity<String> setDefaultAddress(

            @PathVariable Long id

    ) {

        return ResponseEntity.ok(

                addressService.setDefaultAddress(id)

        );

    }

}