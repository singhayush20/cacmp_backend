package com.ayushsingh.cacmp_backend.models.projections.complaint;

public interface ComplaintListDetailsProjection {

    String getComplaintToken();

    String getComplaintSubject();

    String getComplaintDescription();

    String getComplaintStatus();

    String getComplaintPriority();

    String getComplaintCategory();

}
