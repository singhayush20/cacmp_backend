package com.ayushsingh.cacmp_backend.models.dtos.alertDtos;

import com.ayushsingh.cacmp_backend.models.constants.PublishStatus;
import com.ayushsingh.cacmp_backend.models.projections.alertDocument.AlertDocumentUrlProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlertDetailsDto {
    private String alertToken;

    private String subject;

    private String message;

    private List<String> alertImages = new ArrayList<>();

    private List<AlertDocumentUrlProjection> alertDocuments =new ArrayList<>();

    private PublishStatus publishStatus;

    private Date publishedOn;
}
