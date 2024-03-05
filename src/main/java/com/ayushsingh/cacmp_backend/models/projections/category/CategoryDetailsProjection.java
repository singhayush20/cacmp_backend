package com.ayushsingh.cacmp_backend.models.projections.category;

public interface CategoryDetailsProjection {
    String getCategoryName();
    String getCategoryDescription();

    String getDepartmentToken();

    String getDepartmentName();
}
