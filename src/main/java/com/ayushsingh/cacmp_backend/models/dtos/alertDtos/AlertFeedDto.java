package com.ayushsingh.cacmp_backend.models.dtos.alertDtos;

import com.ayushsingh.cacmp_backend.models.constants.AlertInputType;
import com.ayushsingh.cacmp_backend.models.constants.PublishStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlertFeedDto {
    private String alertToken;

    private String subject;

    private Date publishedOn;

    private AlertInputType alertInputType;
}
