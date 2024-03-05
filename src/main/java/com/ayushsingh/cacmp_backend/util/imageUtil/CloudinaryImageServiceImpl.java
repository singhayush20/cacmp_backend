package com.ayushsingh.cacmp_backend.util.imageUtil;

import com.ayushsingh.cacmp_backend.util.exceptionUtil.ApiException;
import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class CloudinaryImageServiceImpl implements ImageUploadService {

    private final Cloudinary cloudinary;


    @Override
    public Map upload(MultipartFile file) {

        try {
            log.info("Uploading file to cloudinary...");
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), Map.of());
            log.info("Upload result: {}", uploadResult);
            return uploadResult;
        }
        catch (IOException e){
            throw  new ApiException("Error uploading the image");
        }
    }
}
