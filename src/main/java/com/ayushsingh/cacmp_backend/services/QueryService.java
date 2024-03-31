package com.ayushsingh.cacmp_backend.services;

import com.ayushsingh.cacmp_backend.models.dtos.queryDtos.QueryCreateDto;
import com.ayushsingh.cacmp_backend.models.dtos.queryDtos.QueryDetailsDto;
import com.ayushsingh.cacmp_backend.repository.paginationDto.PaginationDto;

import java.util.List;

public interface QueryService {

    String createQuery(QueryCreateDto queryCreateDto);

    List<QueryDetailsDto> getQueries(PaginationDto paginationDto);

    String deleteQuery(String queryToken);

}
