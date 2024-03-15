package com.ayushsingh.cacmp_backend.services;

import com.ayushsingh.cacmp_backend.models.dtos.pollDto.*;
import com.ayushsingh.cacmp_backend.models.projections.poll.PollListProjection;
import com.ayushsingh.cacmp_backend.repository.filterDto.PollFilter;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface PollService {

    String createPoll (PollCreateDto pollCreateDto);

    String deletePoll(String pollToken);

    String castVote(VoteDto voteDto);

    PollDetailsDto getPollDetails(String pollToken);

    List<PollListProjection> getPollList();

    String changeLiveStatus(PollStatusDto pollStatusDto);

    List<PollListDto> listPollsByDept(PollFilter pollFilter, Sort sort);
}
