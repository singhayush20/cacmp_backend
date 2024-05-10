package com.ayushsingh.cacmp_backend.config.drive;

import com.ayushsingh.cacmp_backend.util.exceptionUtil.ApiException;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Configuration
public class GoogleDriveConfig {

    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    private static String getPathToGoogleCredentials() {
        String currentDirectory = System.getProperty("user.dir");
        Path filePath = Paths.get(currentDirectory, "cacmp-417114-3b716d377f0a.json");
        return filePath.toString();
    }

    @Bean
    public Drive createDriveService() throws GeneralSecurityException, IOException {

        GoogleCredential credential = GoogleCredential.fromStream(new FileInputStream(getPathToGoogleCredentials()))
                .createScoped(Collections.singleton(DriveScopes.DRIVE));

        try {
            return new Drive.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    JSON_FACTORY,
                    credential)
                    .build();
        } catch (GeneralSecurityException | IOException e) {
            throw new ApiException(e.getMessage());
        }
    }
}
