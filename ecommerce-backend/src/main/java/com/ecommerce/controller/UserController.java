package com.ecommerce.controller;

import com.ecommerce.dto.UpdateProfileRequest;
import com.ecommerce.entity.User;
import com.ecommerce.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // ==========================
    // Get Current User Profile
    // ==========================

    @GetMapping("/profile")
    public ResponseEntity<User> getProfile() {

        return ResponseEntity.ok(
                userService.getCurrentUser()
        );

    }

    // ==========================
    // Update Current User Profile
    // ==========================

    @PutMapping("/profile")
    public ResponseEntity<User> updateProfile(

            @Valid
            @RequestBody
            UpdateProfileRequest request

    ) {

        return ResponseEntity.ok(

                userService.updateProfile(request)

        );

    }

    // ==========================
    // Get All Users
    // ==========================

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {

        return ResponseEntity.ok(
                userService.getAllUsers()
        );

    }

    // ==========================
    // Get User By Id
    // ==========================

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                userService.getUserById(id)
        );

    }

    // ==========================
    // Delete User
    // ==========================

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                userService.deleteUser(id)
        );

    }

}