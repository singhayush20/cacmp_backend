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
public class AlertDeptListDto {

    private String alertToken;

    private String subject;

    private PublishStatus publishStatus;

    private Date createdAt;

    private Date publishedOn;

    private AlertInputType alertInputType;

}
