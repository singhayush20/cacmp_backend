package com.ayushsingh.cacmp_backend.models.securityModels.jwt;


import com.ayushsingh.cacmp_backend.models.entities.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@DiscriminatorValue("USER")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRefreshToken extends RefreshToken{

    @OneToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH,CascadeType.PERSIST},fetch = FetchType.LAZY)
    @JoinColumn(name="user_id",referencedColumnName = "user_id")
    private User user;
}
