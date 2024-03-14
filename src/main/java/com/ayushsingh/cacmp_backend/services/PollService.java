package com.ayushsingh.cacmp_backend.services;

import com.ayushsingh.cacmp_backend.models.dtos.pollDto.PollCreateDto;
import com.ayushsingh.cacmp_backend.models.dtos.pollDto.PollDetailsDto;
import com.ayushsingh.cacmp_backend.models.dtos.pollDto.PollListDto;
import com.ayushsingh.cacmp_backend.models.projections.poll.PollListProjection;
import com.ayushsingh.cacmp_backend.repository.filterDto.PollFilter;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface PollService {

    String createPoll (PollCreateDto pollCreateDto);
    String castVote(String pollToken, String choiceToken);

    PollDetailsDto getPollDetails(String pollToken);

    List<PollListProjection> getPollList();

    String changeLiveStatus(String pollToken, Boolean isLive);

    List<PollListDto> listPollsByDept(PollFilter pollFilter, Sort sort);
}
