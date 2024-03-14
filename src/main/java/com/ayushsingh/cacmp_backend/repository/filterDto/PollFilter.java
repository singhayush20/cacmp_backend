package com.ayushsingh.cacmp_backend.repository.filterDto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PollFilter {

    private Boolean isLive;
    private String deptToken;
}
