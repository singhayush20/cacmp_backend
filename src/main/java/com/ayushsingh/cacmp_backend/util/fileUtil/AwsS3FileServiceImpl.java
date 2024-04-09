package com.ayushsingh.cacmp_backend.util.fileUtil;

import com.ayushsingh.cacmp_backend.config.awsS3Config.AwsS3ConfigurationProperties;
import com.ayushsingh.cacmp_backend.models.dtos.driveFileDtos.UploadedFileDto;
import com.ayushsingh.cacmp_backend.util.exceptionUtil.ApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
@Profile("prod")
public class AwsS3FileServiceImpl implements FileService{

    private final S3Client s3Client;
    private final AwsS3ConfigurationProperties awsS3ConfigurationProperties;
    @Override
    public UploadedFileDto uploadFile(MultipartFile multipartFile) {
        try {
            File file = convertMultipartFileToFile(multipartFile);
            String key = multipartFile.getOriginalFilename();

            PutObjectResponse res = s3Client.putObject(PutObjectRequest.builder()
                            .bucket(awsS3ConfigurationProperties.bucketName())
                            .key(key)
                            .build(),
                    RequestBody.fromFile(file));

            UploadedFileDto uploadedFileDto = new UploadedFileDto();
            uploadedFileDto.setFileId(res.serverSideEncryption().toString());
            uploadedFileDto.setFileExtension(getFileExtension(multipartFile.getOriginalFilename()));
            uploadedFileDto.setFileName(multipartFile.getOriginalFilename());
            uploadedFileDto.setFileUrl(getFileUrl(awsS3ConfigurationProperties.bucketName(), key));

            return uploadedFileDto;
        } catch (S3Exception | IOException e) {
            log.error("Error uploading the file {}", e.getMessage());
            throw new ApiException("Error uploading the file");
        }
    }

    private String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    private String getFileUrl(String bucketName, String key) {
        return String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, awsS3ConfigurationProperties.region(), key);
    }

    public File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        File file = File.createTempFile(multipartFile.getOriginalFilename(), null);
        FileCopyUtils.copy(multipartFile.getBytes(), file);
        return file;
    }

    @Override
    public Boolean deleteFile(String fileName) {
        try {
            s3Client.deleteObject(DeleteObjectRequest.builder()
                    .bucket(awsS3ConfigurationProperties.bucketName())
                    .key(fileName)
                    .build());

            return true;
        } catch (S3Exception e) {
            log.error("Error uploading the file {}", e.getMessage());
            throw new ApiException("Error deleting the file");
        }
    }
}
