package com.ayushsingh.cacmp_backend.util.imageUtil;

import com.ayushsingh.cacmp_backend.util.exceptionUtil.ApiException;
import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class CloudinaryImageServiceImpl implements ImageService {

    private final Cloudinary cloudinary;


    @Override
    public Map<String, Object> uploadComplaintImage (MultipartFile file) {

        try {
            log.info("Uploading file to cloudinary...");
            Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(), Map.of());
            log.info("Upload result: {}", uploadResult);
            return uploadResult;
        } catch (IOException e) {
            throw new ApiException("Error uploading the complaint image");
        }
    }

    @Override
    public Map<String, Object> uploadAlertImage (MultipartFile file) {
        try {
            log.info("Uploading alert image...");
            Map<String, Object> uploadOptions = new HashMap<>();
            uploadOptions.put("folder", "Alert Images");
            uploadOptions.put("display_name",file.getOriginalFilename());
            return (Map<String, Object>) cloudinary.uploader().upload(file, uploadOptions);
        } catch (IOException e) {
            throw new ApiException("Error uploading the alert image");
        }
    }

    @Override
    public Map<String, Object> uploadArticleImage (MultipartFile file) {
        try {
            log.info("Uploading article image...");
            Map<String, Object> uploadOptions = new HashMap<>();
            uploadOptions.put("folder", "Article Images");
            uploadOptions.put("display_name",file.getOriginalFilename());
           return (Map<String, Object>) cloudinary.uploader().upload(file, uploadOptions);

        } catch (IOException e) {
            throw new ApiException("Error uploading the article image");
        }
    }

    @Override
    public Map<String, Object> uploadArticleVideo (MultipartFile file) {
        try {
            log.info("Uploading article image...");
            Map<String, Object> uploadOptions = new HashMap<>();
            uploadOptions.put("folder", "Article Videos");
            uploadOptions.put("display_name",file.getOriginalFilename());
            return (Map<String, Object>) cloudinary.uploader().upload(file, uploadOptions);

        } catch (IOException e) {
            throw new ApiException("Error uploading the article video");
        }
    }
}
