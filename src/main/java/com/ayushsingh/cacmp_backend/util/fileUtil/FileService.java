package com.ayushsingh.cacmp_backend.util.fileUtil;

import com.ayushsingh.cacmp_backend.models.dtos.driveFileDtos.UploadedFileDto;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    UploadedFileDto uploadFile(MultipartFile file);

    default Boolean deleteFile(String fileId){
        return null;
    }
}
