package com.ayushsingh.cacmp_backend.util.driveUtil;

import com.ayushsingh.cacmp_backend.config.drive.DriveConfigurationProperties;
import com.ayushsingh.cacmp_backend.models.dtos.driveFileDtos.UploadedFileDto;
import com.ayushsingh.cacmp_backend.util.exceptionUtil.ApiException;
import com.google.api.client.http.FileContent;

import com.google.api.services.drive.Drive;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService{

    private final Drive drive;
    private final DriveConfigurationProperties driveConfigurationProperties;

    @Override
    public UploadedFileDto uploadFile(MultipartFile multipartFile) {
        try{
            if (multipartFile.isEmpty()) {
               throw new ApiException("File is empty!");
            }
            File file = File.createTempFile("alert_file", null);
            multipartFile.transferTo(file);
            com.google.api.services.drive.model.File fileMetaData = new com.google.api.services.drive.model.File();
            fileMetaData.setName(multipartFile.getOriginalFilename());
            fileMetaData.setParents(Collections.singletonList(driveConfigurationProperties.folderId()));
            FileContent mediaContent = new FileContent(multipartFile.getContentType(), file);
            com.google.api.services.drive.model.File uploadedFile = drive.files().create(fileMetaData, mediaContent)
                    .setFields("id, name,  fileExtension")
                    .execute();
            String imageUrl = driveConfigurationProperties.urlPrefix()+uploadedFile.getId();
            UploadedFileDto uploadedFileDto=new UploadedFileDto();
            uploadedFileDto.setFileExtension(uploadedFile.getFileExtension());
            uploadedFileDto.setFileId(uploadedFile.getId());
            uploadedFileDto.setFileName(uploadedFile.getName());
            uploadedFileDto.setFileUrl(imageUrl);
            return uploadedFileDto;
        } catch (java.io.IOException e) {
            throw new ApiException(e.getMessage());
        }
    }
}
