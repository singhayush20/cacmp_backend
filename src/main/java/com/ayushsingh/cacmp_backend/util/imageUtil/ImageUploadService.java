package com.ayushsingh.cacmp_backend.util.imageUtil;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface ImageUploadService {

    public Map upload(MultipartFile file);
}
