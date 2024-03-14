package com.ayushsingh.cacmp_backend.util.driveUtil;

import com.ayushsingh.cacmp_backend.models.dtos.driveFileDtos.UploadedFileDto;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    UploadedFileDto uploadFile(MultipartFile file);
}
