package com.ayushsingh.cacmp_backend.models.dtos.driveFileDtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UploadedFileDto {

    private String fileId;
    private String fileExtension;
    private String fileName;
    private String fileUrl;
}
