package com.ecommerce.controller;

import com.ecommerce.dto.UploadResponse;
import com.ecommerce.service.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/upload")
@RequiredArgsConstructor
public class UploadController {

    private final UploadService uploadService;

    // ==================================
    // Upload Image
    // ==================================

    @PostMapping
    public ResponseEntity<UploadResponse> uploadImage(

            @RequestParam("file") MultipartFile file) {

        return ResponseEntity.ok(

                uploadService.uploadImage(file)

        );
    }

    // ==================================
    // Delete Image
    // ==================================

    @DeleteMapping("/{publicId}")
    public ResponseEntity<String> deleteImage(

            @PathVariable String publicId) {

        return ResponseEntity.ok(

                uploadService.deleteImage(publicId)

        );
    }

}