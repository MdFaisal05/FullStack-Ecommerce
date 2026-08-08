package com.ecommerce.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.ecommerce.dto.UploadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class UploadServiceImpl implements UploadService {

    private final Cloudinary cloudinary;

    @Override
    public UploadResponse uploadImage(MultipartFile file) {

        try {

            Map<?, ?> result = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.emptyMap()
            );

            return UploadResponse.builder()
                    .imageUrl(result.get("secure_url").toString())
                    .publicId(result.get("public_id").toString())
                    .message("Image Uploaded Successfully")
                    .build();

        } catch (Exception e) {

            throw new RuntimeException("Image Upload Failed");

        }

    }

    @Override
    public String deleteImage(String publicId) {

        try {

            cloudinary.uploader().destroy(
                    publicId,
                    ObjectUtils.emptyMap()
            );

            return "Image Deleted Successfully";

        } catch (Exception e) {

            throw new RuntimeException("Image Delete Failed");

        }

    }

}