package com.ecommerce.service;

import com.ecommerce.dto.UpdateProfileRequest;
import com.ecommerce.entity.User;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    // ==========================
    // Current Logged In User
    // ==========================

    @Override
    public User getCurrentUser() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User Not Found"));

    }

    // ==========================
    // Update Profile
    // ==========================

    @Override
    public User updateProfile(UpdateProfileRequest request) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User Not Found"));

        user.setFirstName(request.getFirstName());

        user.setLastName(request.getLastName());

        if (request.getPassword() != null &&
                !request.getPassword().trim().isEmpty()) {

            user.setPassword(
                    passwordEncoder.encode(request.getPassword())
            );

        }

        return userRepository.save(user);

    }

    // ==========================
    // Get All Users
    // ==========================

    @Override
    public List<User> getAllUsers() {

        return userRepository.findAll();

    }

    // ==========================
    // Get User By Id
    // ==========================

    @Override
    public User getUserById(Long id) {

        return userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User Not Found"));

    }

    // ==========================
    // Delete User
    // ==========================

    @Override
    public String deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User Not Found"));

        userRepository.delete(user);

        return "User Deleted Successfully";

    }

}