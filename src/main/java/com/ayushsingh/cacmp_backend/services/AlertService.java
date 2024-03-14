package com.ayushsingh.cacmp_backend.services;

import com.ayushsingh.cacmp_backend.models.dtos.alertDtos.AlertCreateDto;
import com.ayushsingh.cacmp_backend.models.dtos.alertDtos.AlertDetailsDto;
import com.ayushsingh.cacmp_backend.models.dtos.alertDtos.StatusUpdateDto;
import org.springframework.web.multipart.MultipartFile;

public interface AlertService {

    String createAlert(AlertCreateDto alertCreateDto);

    AlertDetailsDto getAlertDetails(String token);

    String saveAlertImages(String token, MultipartFile[] images);

    String updateStatus(StatusUpdateDto statusUpdateDto);

    String uploadFile(String alertToken, MultipartFile[] multipartFiles);
}
