package com.ayushsingh.cacmp_backend.services.serviceimpl;

import com.ayushsingh.cacmp_backend.models.dtos.pollDto.*;
import com.ayushsingh.cacmp_backend.models.entities.*;
import com.ayushsingh.cacmp_backend.models.projections.poll.PollListProjection;
import com.ayushsingh.cacmp_backend.repository.entities.*;
import com.ayushsingh.cacmp_backend.repository.filterDto.PollFilter;
import com.ayushsingh.cacmp_backend.repository.specifications.poll.PollSpecification;
import com.ayushsingh.cacmp_backend.services.PollService;
import com.ayushsingh.cacmp_backend.util.exceptionUtil.ApiException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PollServiceImpl implements PollService {

    private final PollRepository pollRepository;
    private final DepartmentRepository departmentRepository;
    private final PollChoiceRepository pollChoiceRepository;
    private final VoteRepository voteRepository;
    private final ConsumerRepository consumerRepository;

    @Transactional
    @Override
    public String createPoll(PollCreateDto pollCreateDto) {
        Department department = this.departmentRepository.findByDepartmentToken(pollCreateDto.getDeptToken()).orElseThrow(() -> new ApiException("Department not found"));
        Poll poll = new Poll();
        poll.setDepartment(department);
        poll.setDescription(pollCreateDto.getDescription());
        poll.setSubject(pollCreateDto.getSubject());
        Set<PollChoice> pollChoices = new HashSet<>();
        int sequenceNo=1;
        for (String pollChoice : pollCreateDto.getPollChoices()) {
            PollChoice choice = new PollChoice();
            choice.setChoiceName(pollChoice);
            choice.setPoll(poll);
            choice.setSequenceNo(sequenceNo++);
            pollChoices.add(choice);
        }
        poll.setChoices(pollChoices);
        return pollRepository.save(poll).getPollToken();
    }

    @Transactional
    @Override
    public String deletePoll (String pollToken) {
        Boolean isPollActive = pollRepository.findIfPollActive(pollToken);
        if (!isPollActive) {
            Poll poll=pollRepository.findByToken(pollToken).orElseThrow(() -> new ApiException("Poll not found"));
            pollRepository.delete(poll);
        }
        else{
            throw new ApiException("Poll cannot be deleted as it is live");
        }
        return pollToken;
    }

    @Transactional
    @Override
    public String castVote(VoteDto voteDto) {
        Poll poll=this.pollRepository.findByToken(voteDto.getPollToken()).orElseThrow(() -> new ApiException("Poll not found"));
        if(!poll.getIsLive()){
            throw new ApiException("Poll is not live. Therefore, vote cannot be cast!");
        }
        Consumer consumer=this.consumerRepository.findByConsumerToken(voteDto.getConsumerToken()).orElseThrow(() -> new ApiException("Consumer not found"));
        Vote vote=new Vote();
        vote.setConsumer(consumer);
        vote.setPoll(poll);
        vote=voteRepository.save(vote);
        pollChoiceRepository.addVote(voteDto.getPollChoiceToken());
        return vote.getVoteToken();
    }

    @Override
    public PollDetailsDto getPollDetails(String pollToken) {
        Poll poll = this.pollRepository.findByToken(pollToken).orElseThrow(() -> new ApiException("Poll not found"));
        PollDetailsDto pollDetailsDto = new PollDetailsDto();
        pollDetailsDto.setPollToken(poll.getPollToken());
        pollDetailsDto.setSubject(poll.getSubject());
        pollDetailsDto.setDescription(poll.getDescription());
        Map<String,String> departmentDetails=pollRepository.getDeptNameAndToken(pollToken);
        pollDetailsDto.setDeptToken(departmentDetails.get("deptToken"));
        pollDetailsDto.setDepartmentName(departmentDetails.get("departmentName"));
        pollDetailsDto.setIsLive(poll.getIsLive());
        pollDetailsDto.setPollChoiceDetails(poll.getChoices().stream().map(pollChoice -> {
            PollChoiceDetailsDto pollChoiceDetailsDto = new PollChoiceDetailsDto();
            pollChoiceDetailsDto.setChoiceToken(pollChoice.getChoiceToken());
            pollChoiceDetailsDto.setChoiceName(pollChoice.getChoiceName());
            pollChoiceDetailsDto.setVoteCount(pollChoice.getVoteCount());
            pollChoiceDetailsDto.setSequenceNo(pollChoice.getSequenceNo());
            return pollChoiceDetailsDto;
        }).collect(Collectors.toList()));
        return pollDetailsDto;
    }

    @Override
    public List<PollListProjection> getPollList() {
        return this.pollRepository.getPollList();
    }

    @Transactional
    @Override
    public String changeLiveStatus(PollStatusDto pollStatusDto) {
        this.pollRepository.changeLiveStatus(pollStatusDto.getToken(),pollStatusDto.getStatus());
        return pollStatusDto.getToken();
    }

    @Override
    public List<PollListDto> listPollsByDept(PollFilter pollFilter, Sort sort) {
        Specification<Poll> spec = PollSpecification.filterPolls(pollFilter);
        List<Poll> pollList = pollRepository.findAll(spec, sort);
        List<PollListDto> polls=new ArrayList<>();
        pollList.forEach(poll -> {
            PollListDto pollListDto = new PollListDto();
            pollListDto.setPollToken(poll.getPollToken());
            pollListDto.setSubject(poll.getSubject());
            pollListDto.setDescription(poll.getDescription());
            pollListDto.setIsLive(poll.getIsLive());
            pollListDto.setCreatedOn(poll.getCreatedAt());
            polls.add(pollListDto);
        });
        return polls;
    }
}
