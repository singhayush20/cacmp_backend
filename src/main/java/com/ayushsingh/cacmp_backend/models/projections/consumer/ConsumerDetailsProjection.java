package com.ayushsingh.cacmp_backend.models.projections.consumer;

public interface ConsumerDetailsProjection {

    String getConsumerToken();
    String getName();
    String getEmail();
    String getPhone();
    String getGender();
    String getHouseNo();
    String getWardNo();
    String getLocality();
    String getPinCode();
    String getCity();
    String getState();

}
