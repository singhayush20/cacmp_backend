package com.ayushsingh.cacmp_backend.models.projections.complaint;

import java.util.List;

public interface ComplaintDetailsProjection {
    String getComplaintToken();

    String getComplaintSubject();

    String getComplaintDescription();

    String getComplaintStatus();

    String getComplaintPriority();

    Long getPincode();

    String getAddress();

    String getWardNo();

    Long getContactNo();

    String getConsumerToken();

    String getConsumerName();

    String getConsumerPhone();


}
