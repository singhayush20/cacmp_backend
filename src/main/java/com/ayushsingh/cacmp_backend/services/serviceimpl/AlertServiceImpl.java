package com.ayushsingh.cacmp_backend.services.serviceimpl;

import com.ayushsingh.cacmp_backend.models.constants.AlertInputType;
import com.ayushsingh.cacmp_backend.models.constants.PublishStatus;
import com.ayushsingh.cacmp_backend.models.dtos.alertDtos.*;
import com.ayushsingh.cacmp_backend.models.dtos.driveFileDtos.UploadedFileDto;
import com.ayushsingh.cacmp_backend.models.entities.Alert;
import com.ayushsingh.cacmp_backend.models.entities.AlertDocument;
import com.ayushsingh.cacmp_backend.models.entities.AlertImage;
import com.ayushsingh.cacmp_backend.models.entities.Department;
import com.ayushsingh.cacmp_backend.models.projections.alertDocument.AlertDocumentUrlProjection;
import com.ayushsingh.cacmp_backend.repository.entities.AlertDocumentRepository;
import com.ayushsingh.cacmp_backend.repository.entities.AlertImageRepository;
import com.ayushsingh.cacmp_backend.repository.entities.AlertRepository;
import com.ayushsingh.cacmp_backend.repository.entities.DepartmentRepository;
import com.ayushsingh.cacmp_backend.repository.filterDto.AlertFilter;
import com.ayushsingh.cacmp_backend.repository.paginationDto.PaginationDto;
import com.ayushsingh.cacmp_backend.repository.specifications.alert.AlertSpecification;
import com.ayushsingh.cacmp_backend.services.AlertService;
import com.ayushsingh.cacmp_backend.util.driveUtil.FileService;
import com.ayushsingh.cacmp_backend.util.exceptionUtil.ApiException;
import com.ayushsingh.cacmp_backend.util.imageUtil.ImageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlertServiceImpl implements AlertService {

    private final AlertRepository alertRepository;

    private final AlertImageRepository alertImageRepository;

    private final AlertDocumentRepository alertDocumentRepository;

    private final ImageService imageService;

    private final FileService fileService;

    private final DepartmentRepository departmentRepository;

    @Override
    public String createAlert (AlertCreateDto alertCreateDto) {
        Department department = this.departmentRepository.findByDepartmentToken(alertCreateDto.getDepartmentToken()).orElseThrow(() -> new ApiException("Department not found"));
        Alert alert = new Alert();
        alert.setDepartment(department);
        alert.setInputType(alertCreateDto.getAlertInputType());
        alert.setMessage(alertCreateDto.getMessage());
        alert.setSubject(alertCreateDto.getSubject());
        alert.setPublishStatus(PublishStatus.DRAFT);
        return alertRepository.save(alert).getAlertToken();
    }

    @Override
    public AlertDetailsDto getAlertDetails (String token) {
        Alert alert = alertRepository.findByAlertToken(token).orElseThrow(() -> new ApiException("Alert not found"));
        AlertDetailsDto alertDetailsDto = new AlertDetailsDto();
        alertDetailsDto.setAlertToken(token);
        alertDetailsDto.setSubject(alert.getSubject());
        alertDetailsDto.setMessage(alert.getMessage());
        alertDetailsDto.setAlertInputType(alert.getInputType());
        alertDetailsDto.setPublishStatus(alert.getPublishStatus());
        alertDetailsDto.setPublishedOn(alert.getPublishedOn());
        List<String> imageUrls = this.alertImageRepository.findAllUrlsByAlertId(token);
        alertDetailsDto.setAlertImages(imageUrls);
        List<AlertDocumentUrlProjection> documentUrlProjections = this.alertDocumentRepository.findAllByAlertToken(token);
        alertDetailsDto.setAlertDocuments(documentUrlProjections);
        return alertDetailsDto;
    }

    @Transactional
    @Override
    public String saveAlertImages (String token, MultipartFile[] images) {
        Alert alert = this.alertRepository.findByAlertToken(token).orElseThrow(() -> new ApiException("Alert not found"));
        for (MultipartFile image : images) {
            Map<String, Object> uploadResult = imageService.uploadAlertImage(image);
            AlertImage alertImage = new AlertImage();
            alertImage.setImageUrl((String) uploadResult.get("secure_url"));
            alertImage.setPublicId((String) uploadResult.get("public_id"));
            alertImage.setAssetId((String) uploadResult.get("asset_id"));
            alertImage.setSignature((String) uploadResult.get("signature"));
            alertImage.setAlert(alert);
            alertImageRepository.save(alertImage);
        }
        return alert.getAlertToken();
    }

    @Transactional
    @Override
    public String updateStatus (StatusUpdateDto statusUpdateDto) {
        String alertToken = statusUpdateDto.getToken();
        PublishStatus publishStatus = statusUpdateDto.getPublishStatus();
        Alert alert = this.alertRepository.findByAlertToken(alertToken).orElseThrow(() -> new ApiException("Alert not found"));
        if (alert.getPublishStatus() == publishStatus) {
            throw new ApiException("New and old status cannot be same!");
        }

        alert.setPublishStatus(publishStatus);
        if (publishStatus == PublishStatus.PUBLISHED) {
            alert.setPublishedOn(new Date());
        }
        this.alertRepository.save(alert);

        return statusUpdateDto.getToken();
    }

    @Transactional
    @Override
    public String uploadFiles (String alertToken, MultipartFile[] multipartFiles) {
        Alert alert = alertRepository.findByAlertToken(alertToken).orElseThrow(() -> new ApiException("Alert not found!"));
        for (MultipartFile file : multipartFiles) {
            UploadedFileDto fileDto = fileService.uploadFile(file);
            AlertDocument alertDocument = new AlertDocument();
            alertDocument.setDocumentName(fileDto.getFileName());
            alertDocument.setDocumentUrl(fileDto.getFileUrl());
            alertDocument.setFormat(fileDto.getFileExtension());
            alertDocument.setDocumentToken(fileDto.getFileId());
            alertDocument.setAlert(alert);

            alertDocumentRepository.save(alertDocument);
        }
        return alert.getAlertToken();
    }

    @Override
    public List<AlertDeptListDto> listAlertsByDepartment (AlertFilter alertFilter, Sort sort) {
        Specification<Alert> spec = AlertSpecification.filterAlerts(alertFilter);
        List<Alert> alerts = alertRepository.findAll(spec, sort);
        return alerts.stream().map(alert -> {
            AlertDeptListDto alertListDto = new AlertDeptListDto();
            alertListDto.setAlertToken(alert.getAlertToken());
            alertListDto.setSubject(alert.getSubject());
            alertListDto.setPublishStatus(alert.getPublishStatus());
            alertListDto.setCreatedAt(alert.getCreatedAt());
            alertListDto.setAlertInputType(alert.getInputType());
            alertListDto.setPublishedOn(alert.getPublishedOn());
            return alertListDto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<AlertFeedDto> getAlertFeed (PaginationDto paginationDto) {
        Sort sort = null;
        if (paginationDto.getSortBy() != null) {
            sort = Sort.by(paginationDto.getSortBy());

            if ("dsc".equals(paginationDto.getSortDirection())) {
                sort = sort.descending();
            } else {
                sort = sort.ascending();
            }
        } else {
            sort = Sort.by("publishedOn").descending();
        }
        Pageable pageable = PageRequest.of(paginationDto.getPageNumber(), paginationDto.getPageSize(), sort);
        Page<Alert> alertPage = alertRepository.findAllByPublishStatus(PublishStatus.PUBLISHED,pageable);
        List<Alert> alerts=alertPage.getContent();
        return alerts.stream().map(alert -> {
            AlertFeedDto alertFeedDto = new AlertFeedDto();
            alertFeedDto.setAlertToken(alert.getAlertToken());
            alertFeedDto.setSubject(alert.getSubject());
            alertFeedDto.setPublishedOn(alert.getPublishedOn());
            alertFeedDto.setAlertInputType(alert.getInputType());
            if(alert.getInputType()== AlertInputType.DOCUMENT){
                List<AlertDocumentUrlProjection> documentUrlProjections = this.alertDocumentRepository.findAllByAlertToken(alert.getAlertToken());
                alertFeedDto.setAlertDocuments(documentUrlProjections);
            }
            return alertFeedDto;
        }).collect(Collectors.toList());
    }
}
