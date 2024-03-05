package com.ayushsingh.cacmp_backend.util.imageUtil;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface ImageService {

     Map<String,Object> upload(MultipartFile file);
}
