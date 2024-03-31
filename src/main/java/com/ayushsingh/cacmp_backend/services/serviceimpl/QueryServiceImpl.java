package com.ayushsingh.cacmp_backend.services.serviceimpl;

import com.ayushsingh.cacmp_backend.models.dtos.queryDtos.QueryCreateDto;
import com.ayushsingh.cacmp_backend.models.dtos.queryDtos.QueryDetailsDto;
import com.ayushsingh.cacmp_backend.models.entities.Article;
import com.ayushsingh.cacmp_backend.models.entities.UserQuery;
import com.ayushsingh.cacmp_backend.repository.UserQueryRepository;
import com.ayushsingh.cacmp_backend.repository.paginationDto.PaginationDto;
import com.ayushsingh.cacmp_backend.services.QueryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class QueryServiceImpl implements QueryService {

    private final UserQueryRepository userQueryRepository;
    private final ModelMapper modelMapper;
    @Override
    public String createQuery(QueryCreateDto queryCreateDto) {
        UserQuery query=modelMapper.map(queryCreateDto,UserQuery.class);
        return userQueryRepository.save(query).getQueryToken();
    }

    @Override
    public List<QueryDetailsDto> getQueries(PaginationDto paginationDto) {
        Sort sort;
        if (paginationDto.getSortBy() != null) {
            sort = Sort.by(paginationDto.getSortBy());

            if ("dsc".equals(paginationDto.getSortDirection())) {
                sort = sort.descending();
            } else {
                sort = sort.ascending();
            }
        } else {
            sort = Sort.by("publishDate").descending();
        }

        Pageable pageable = PageRequest.of(paginationDto.getPageNumber(), paginationDto.getPageSize(), sort);
        Page<UserQuery> queries = this.userQueryRepository.findAll(pageable);
        Long total= userQueryRepository.count();
        List<QueryDetailsDto> queryDetailsDtos = new ArrayList<>();
        for(UserQuery query:queries){
            QueryDetailsDto queryDetailsDto=modelMapper.map(query,QueryDetailsDto.class);
            queryDetailsDto.setCount(total);
            queryDetailsDtos.add(queryDetailsDto);
        }
        return queryDetailsDtos;
    }

    @Transactional
    @Override
    public String deleteQuery(String queryToken) {
        this.userQueryRepository.deleteByToken(queryToken);
        return "Query with token "+queryToken+" deleted successfully";
    }


}
