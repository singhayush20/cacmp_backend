package com.ayushsingh.cacmp_backend.models.projections.poll;

import java.util.Date;

public interface PollListProjection {

    String getPollToken();
    String getSubject();
    String getDescription();
    String getDeptToken();

    Date getLiveOn();
    String getDepartmentName();
}
