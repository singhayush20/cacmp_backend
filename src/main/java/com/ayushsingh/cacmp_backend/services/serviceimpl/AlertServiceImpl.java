package com.ayushsingh.cacmp_backend.services.serviceimpl;

import com.ayushsingh.cacmp_backend.models.constants.PublishStatus;
import com.ayushsingh.cacmp_backend.models.dtos.alertDtos.AlertCreateDto;
import com.ayushsingh.cacmp_backend.models.dtos.alertDtos.AlertDetailsDto;
import com.ayushsingh.cacmp_backend.models.dtos.alertDtos.AlertStatusDto;
import com.ayushsingh.cacmp_backend.models.dtos.driveFileDtos.UploadedFileDto;
import com.ayushsingh.cacmp_backend.models.entities.Alert;
import com.ayushsingh.cacmp_backend.models.entities.AlertDocument;
import com.ayushsingh.cacmp_backend.models.entities.AlertImage;
import com.ayushsingh.cacmp_backend.models.projections.alertDocument.AlertDocumentUrlProjection;
import com.ayushsingh.cacmp_backend.repository.entities.AlertDocumentRepository;
import com.ayushsingh.cacmp_backend.repository.entities.AlertImageRepository;
import com.ayushsingh.cacmp_backend.repository.entities.AlertRepository;
import com.ayushsingh.cacmp_backend.services.AlertService;
import com.ayushsingh.cacmp_backend.util.driveUtil.FileService;
import com.ayushsingh.cacmp_backend.util.exceptionUtil.ApiException;
import com.ayushsingh.cacmp_backend.util.imageUtil.ImageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AlertServiceImpl implements AlertService {

    private final ModelMapper modelMapper;
    private final AlertRepository alertRepository;
    private final AlertImageRepository alertImageRepository;
    private final AlertDocumentRepository alertDocumentRepository;
    private final ImageService imageService;
    private final FileService fileService;
    @Override
    public String createAlert(AlertCreateDto alertCreateDto) {
        Alert alert=new Alert();
        alert.setMessage(alertCreateDto.getMessage());
        alert.setSubject(alertCreateDto.getSubject());
        alert.setPublishStatus(PublishStatus.DRAFT);
        return alertRepository.save(alert).getAlertToken();
    }

    @Override
    public AlertDetailsDto getAlertDetails(String token) {
        Alert alert=alertRepository.findByAlertToken(token).orElseThrow(()->new ApiException("Alert not found"));
        AlertDetailsDto alertDetailsDto=new AlertDetailsDto();
        alertDetailsDto.setSubject(alert.getSubject());
        alertDetailsDto.setMessage(alert.getMessage());
        alertDetailsDto.setPublishStatus(alert.getPublishStatus());
        alertDetailsDto.setPublishedOn(alert.getPublishedOn());
        List<String> imageUrls=this.alertImageRepository.findAllUrlsByAlertId(token);
        alertDetailsDto.setAlertImages(imageUrls);
        List<AlertDocumentUrlProjection> documentUrlProjections=this.alertDocumentRepository.findAllByAlertToken(token);
        alertDetailsDto.setAlertDocuments(documentUrlProjections);
        return alertDetailsDto;
    }

    @Transactional
    @Override
    public String saveAlertImages(String token, MultipartFile[] images) {
        Alert alert=this.alertRepository.findByAlertToken(token).orElseThrow(()->new ApiException("Alert not found"));
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
    public String updateStatus(AlertStatusDto alertStatusDto) {
        alertRepository.updateStatus(alertStatusDto.getAlertToken(),alertStatusDto.getPublishStatus());
        return alertStatusDto.getAlertToken();
    }

    @Transactional
    @Override
    public String uploadFile(String alertToken, MultipartFile[] multipartFiles) {
        Alert alert=alertRepository.findByAlertToken(alertToken).orElseThrow(()->new ApiException("Alert not found!"));
        for(MultipartFile file: multipartFiles){
            UploadedFileDto fileDto=fileService.uploadFile(file);
            AlertDocument alertDocument=new AlertDocument();
            alertDocument.setDocumentName(fileDto.getFileName());
            alertDocument.setDocumentUrl(fileDto.getFileUrl());
            alertDocument.setFormat(fileDto.getFileExtension());
            alertDocument.setDocumentToken(fileDto.getFileId());
            alertDocument.setAlert(alert);
            alertRepository.save(alert);
        }
        return alert.getAlertToken();
    }
}
