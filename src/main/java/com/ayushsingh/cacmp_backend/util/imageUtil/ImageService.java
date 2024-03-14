package com.ayushsingh.cacmp_backend.util.imageUtil;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface ImageService {

     Map<String,Object> uploadComplaintImage(MultipartFile file);

     Map<String,Object> uploadAlertImage(MultipartFile file);

     Map<String,Object> uploadArticleImage(MultipartFile file);

     Map<String,Object> uploadArticleVideo(MultipartFile file);
}
