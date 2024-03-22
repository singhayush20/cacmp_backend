package com.ayushsingh.cacmp_backend.repository.entities;

import com.ayushsingh.cacmp_backend.models.projections.consumer.ConsumerDetailsProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ayushsingh.cacmp_backend.models.entities.Consumer;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ConsumerRepository extends JpaRepository<Consumer, Long> {

    @Query("select c from Consumer c where c.email = ?1")
    Optional<Consumer> findByEmail(String username);

    @Query("select c from Consumer c where c.phone = ?1")
    Optional<Consumer> findByPhone (String phone);

    Boolean existsByEmail(String email);

    Boolean existsByPhone(String phone);

    @Query("select c.consumerToken from Consumer c where c.email = ?1")
    String findTokenByEmail(String email);


    @Query("select c from Consumer c where c.consumerToken = ?1")
    Optional<Consumer> findByConsumerToken(String consumerToken);

    @Query("""
            SELECT
            c.consumerToken as consumerToken,
            c.name as name,
            c.email as email,
            c.phone as phone,
            c.gender as gender,
            c.address.houseNo as houseNo,
            c.address.locality as locality,
            c.address.wardNo as wardNo,
            c.address.pinCode as pinCode,
            c.address.city as city,
            c.address.state as state
            FROM
            Consumer c WHERE c.consumerToken = ?1
            """)
    ConsumerDetailsProjection getConsumerDetails(String token);

    @Query("select c from Consumer c where c.consumerToken = ?1")
   Optional<Consumer> findByUserToken(String userToken);


    @Query("SELECT c.address.wardNo AS wardNo, COUNT(c) AS count FROM Consumer c GROUP BY c.address.wardNo")
    List<Map<String, Long>> countConsumersByWardNo();

    @Query("SELECT c.address.pinCode AS pinCode, COUNT(c) AS count FROM Consumer c GROUP BY c.address.pinCode")
    List<Map<Long, Long>> countConsumersByPinCode();


}
