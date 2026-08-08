package com.ecommerce.service;

import com.ecommerce.dto.UpdateProfileRequest;
import com.ecommerce.entity.User;

import java.util.List;

public interface UserService {

    // ==========================
    // Current Logged In User
    // ==========================

    User getCurrentUser();

    // ==========================
    // Update Logged In User
    // ==========================

    User updateProfile(UpdateProfileRequest request);

    // ==========================
    // Get All Users
    // ==========================

    List<User> getAllUsers();

    // ==========================
    // Get User By Id
    // ==========================

    User getUserById(Long id);

    // ==========================
    // Delete User
    // ==========================

    String deleteUser(Long id);

}