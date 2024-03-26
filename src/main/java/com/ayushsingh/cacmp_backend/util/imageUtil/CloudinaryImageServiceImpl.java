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
    public Map<String, Object> uploadComplaintImage(MultipartFile file) {

        try {
            log.info("Uploading file to cloudinary...");
            Map<String, Object> uploadOptions = new HashMap<>();
            uploadOptions.put("folder", "Complaint Images");
            Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(), uploadOptions);
            log.info("Upload result: {}", uploadResult);
            return uploadResult;
        } catch (IOException e) {
            throw new ApiException("Error uploading the complaint image");
        }
    }

    @Override
    public Map<String, Object> uploadAlertImage(MultipartFile file) {
        try {
            log.info("Uploading alert image...");
            Map<String, Object> uploadOptions = new HashMap<>();
            uploadOptions.put("folder", "Alert Images");
            uploadOptions.put("display_name", file.getOriginalFilename());
            return (Map<String, Object>) cloudinary.uploader().upload(file.getBytes(), uploadOptions);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ApiException("Error uploading the alert image: " + e.getMessage());
        }
    }

    @Override
    public Map<String, Object> uploadArticleImage(MultipartFile file) {
        try {
            log.info("Uploading article image...");
            Map<String, Object> uploadOptions = new HashMap<>();
            uploadOptions.put("folder", "Article Images");
            uploadOptions.put("display_name", file.getOriginalFilename());
            return (Map<String, Object>) cloudinary.uploader().upload(file.getBytes(), uploadOptions);

        } catch (IOException e) {
            log.error("Error uploading the article image: {}", e.getMessage());
            throw new ApiException("Error uploading the article image");
        }
    }

    @Override
    public Map<String, Object> uploadArticleVideo(MultipartFile file) {
        try {
            log.info("Uploading article image...");
            Map<String, Object> uploadOptions = new HashMap<>();
            uploadOptions.put("folder", "Article Videos");
            uploadOptions.put("display_name", file.getOriginalFilename());
            uploadOptions.put("resource_type", "video");
            return (Map<String, Object>) cloudinary.uploader().upload(file.getBytes(), uploadOptions);

        } catch (IOException e) {
            throw new ApiException("Error uploading the article video");
        }
    }

    @Override
    public Boolean deleteArticleVideo(String publicId) {
        log.info("Deleting article video {}", publicId);
        Map<String, Object> uploadOptions = new HashMap<>();
        uploadOptions.put("folder", "Article Videos");
        uploadOptions.put("resource_type", "video");
        try {
            cloudinary.uploader().destroy(publicId, uploadOptions);
        } catch (IOException e) {
            log.error("Error while deleting video: {}", e.getMessage());
            throw new ApiException("Error while deleting video");
        }
        return true;
    }

    @Override
    public Boolean deleteArticleImage(String publicId) {
        log.info("Deleting article image {}", publicId);
        Map<String, Object> uploadOptions = new HashMap<>();
        uploadOptions.put("folder", "Article Images");
        try {
            cloudinary.uploader().destroy(publicId, uploadOptions);
        } catch (IOException e) {
            log.error("Error while deleting image: {}", e.getMessage());
            throw new ApiException("Error while deleting image");
        }
        return true;
    }

    @Override
    public Boolean deleteAlertImage(String publicId) {
        log.info("Deleting alert image {}", publicId);
        Map<String, Object> uploadOptions = new HashMap<>();
        uploadOptions.put("folder", "Alert Images");
        try {
            cloudinary.uploader().destroy(publicId, uploadOptions);
        } catch (IOException e) {
            log.error("Error while deleting image: {}", e.getMessage());
            throw new ApiException("Error while deleting image");
        }
        return true;
    }

    @Override
    public Boolean deleteComplaintImage(String publicId) {
        log.info("Deleting complaint image {}", publicId);
        Map<String, Object> uploadOptions = new HashMap<>();
        uploadOptions.put("folder", "Article Images");
        try {
            cloudinary.uploader().destroy(publicId, uploadOptions);
        } catch (IOException e) {
            log.error("Error while deleting image: {}", e.getMessage());
            throw new ApiException("Error while deleting image");
        }
        return true;
    }
}
