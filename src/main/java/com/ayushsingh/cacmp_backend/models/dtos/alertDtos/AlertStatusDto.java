package com.ayushsingh.cacmp_backend.models.dtos.alertDtos;

import com.ayushsingh.cacmp_backend.models.constants.PublishStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlertStatusDto {

    private String alertToken;
    private PublishStatus publishStatus;
}
