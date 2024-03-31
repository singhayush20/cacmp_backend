package com.ayushsingh.cacmp_backend.controllers;

import com.ayushsingh.cacmp_backend.models.dtos.queryDtos.QueryCreateDto;
import com.ayushsingh.cacmp_backend.models.dtos.queryDtos.QueryDetailsDto;
import com.ayushsingh.cacmp_backend.repository.paginationDto.PaginationDto;
import com.ayushsingh.cacmp_backend.services.QueryService;
import com.ayushsingh.cacmp_backend.util.responseUtil.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/query")
public class UserQueryController {

    private final QueryService queryService;

    @PostMapping("/new")
    public ResponseEntity<ApiResponse<String>> createQuery(@RequestBody QueryCreateDto queryCreateDto) {
        String token=queryService.createQuery(queryCreateDto);
        return new ResponseEntity<>(new ApiResponse<>(token), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<QueryDetailsDto>>> getAllQueries(@RequestParam("sortBy") String sortBy,
                                                                            @RequestParam("sortDirection") String sortDirection,
                                                                            @RequestParam("pageCount") Integer pageCount,
                                                                            @RequestParam("pageSize") Integer pageSize) {
        List<QueryDetailsDto> queryDetailsDtoList=queryService.getQueries(new PaginationDto(pageCount,pageSize,sortBy,sortDirection));
        return new ResponseEntity<>(new ApiResponse<>(queryDetailsDtoList), HttpStatus.OK);
    }

    @DeleteMapping("/{queryId}")
    public ResponseEntity<ApiResponse<String>> deleteQuery(@PathVariable("queryId") String queryId) {
        return new ResponseEntity<>(new ApiResponse<>(queryService.deleteQuery(queryId)), HttpStatus.OK);
    }
}
