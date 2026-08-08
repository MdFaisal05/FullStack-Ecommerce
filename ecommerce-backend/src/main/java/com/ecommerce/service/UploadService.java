package com.ecommerce.service;

import com.ecommerce.dto.UploadResponse;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService {

    UploadResponse uploadImage(MultipartFile file);

    String deleteImage(String publicId);

}