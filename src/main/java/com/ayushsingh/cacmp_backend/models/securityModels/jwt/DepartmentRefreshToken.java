package com.ayushsingh.cacmp_backend.models.securityModels.jwt;

import com.ayushsingh.cacmp_backend.models.entities.Department;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@DiscriminatorValue("DEPARTMENT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentRefreshToken extends RefreshToken{
   @OneToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH,CascadeType.PERSIST},fetch = FetchType.LAZY)
    @JoinColumn(name="department_id",referencedColumnName = "department_id")
    private Department department;
}
