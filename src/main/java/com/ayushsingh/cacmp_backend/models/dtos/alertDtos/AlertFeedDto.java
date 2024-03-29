package com.ayushsingh.cacmp_backend.models.dtos.alertDtos;

import com.ayushsingh.cacmp_backend.models.constants.AlertInputType;
import com.ayushsingh.cacmp_backend.models.constants.PublishStatus;
import com.ayushsingh.cacmp_backend.models.projections.alertDocument.AlertDocumentUrlProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlertFeedDto {
    private String alertToken;

    private String subject;

    private Date publishedOn;

    private AlertInputType alertInputType;

    private List<AlertDocumentUrlProjection> alertDocuments;
}
