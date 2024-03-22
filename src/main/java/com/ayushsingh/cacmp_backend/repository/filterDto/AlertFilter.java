package com.ayushsingh.cacmp_backend.repository.filterDto;

import com.ayushsingh.cacmp_backend.models.constants.PublishStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlertFilter {

    private String alertToken;
    private PublishStatus publishStatus;

}
