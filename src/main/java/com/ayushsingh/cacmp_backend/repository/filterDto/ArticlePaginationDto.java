package com.ayushsingh.cacmp_backend.repository.filterDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticlePaginationDto {
    private Integer pageNumber;
    private Integer pageSize;
    private String sortBy;
    private String sortDirection;
}
