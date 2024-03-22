package com.ayushsingh.cacmp_backend.repository.paginationDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaginationDto {
    private Integer pageNumber;
    private Integer pageSize;
    private String sortBy;
    private String sortDirection;
}
