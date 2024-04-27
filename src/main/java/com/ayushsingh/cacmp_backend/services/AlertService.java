package com.ayushsingh.cacmp_backend.services;

import com.ayushsingh.cacmp_backend.models.dtos.alertDtos.*;
import com.ayushsingh.cacmp_backend.repository.filterDto.AlertFilter;
import com.ayushsingh.cacmp_backend.repository.paginationDto.PaginationDto;
import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AlertService {

    String createAlert(AlertCreateDto alertCreateDto);

    AlertDetailsDto getAlertDetails(String token);

    String saveAlertImages(String token, MultipartFile[] images);

    String updateStatus(StatusUpdateDto statusUpdateDto);

    String uploadFiles (String alertToken, MultipartFile[] multipartFiles);

    List<AlertDeptListDto> listAlertsByDepartment(AlertFilter alertFilter, Sort sort);

    List<AlertFeedDto> getAlertFeed(PaginationDto pageDto);

    String deleteAlert(String alertToken);

}
