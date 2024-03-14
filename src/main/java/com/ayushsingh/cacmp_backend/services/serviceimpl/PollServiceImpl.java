package com.ayushsingh.cacmp_backend.services.serviceimpl;

import com.ayushsingh.cacmp_backend.models.dtos.pollDto.PollChoiceDetailsDto;
import com.ayushsingh.cacmp_backend.models.dtos.pollDto.PollCreateDto;
import com.ayushsingh.cacmp_backend.models.dtos.pollDto.PollDetailsDto;
import com.ayushsingh.cacmp_backend.models.dtos.pollDto.PollListDto;
import com.ayushsingh.cacmp_backend.models.entities.Department;
import com.ayushsingh.cacmp_backend.models.entities.Poll;
import com.ayushsingh.cacmp_backend.models.entities.PollChoice;
import com.ayushsingh.cacmp_backend.models.projections.poll.PollListProjection;
import com.ayushsingh.cacmp_backend.repository.entities.DepartmentRepository;
import com.ayushsingh.cacmp_backend.repository.entities.PollChoiceRepository;
import com.ayushsingh.cacmp_backend.repository.entities.PollRepository;
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

    @Transactional
    @Override
    public String createPoll(PollCreateDto pollCreateDto) {
        Department department = this.departmentRepository.findByDepartmentToken(pollCreateDto.getDeptToken()).orElseThrow(() -> new ApiException("Department not found"));
        Poll poll = new Poll();
        poll.setDepartment(department);
        poll.setDescription(pollCreateDto.getDescription());
        poll.setSubject(poll.getSubject());
        Set<PollChoice> pollChoices = new HashSet<>();
        for (String pollChoice : pollCreateDto.getPollChoices()) {
            PollChoice choice = new PollChoice();
            choice.setChoiceName(pollChoice);
            choice.setPoll(poll);
            pollChoices.add(choice);
        }
        poll.setChoices(pollChoices);
        return pollRepository.save(poll).getPollToken();
    }

    @Override
    public String castVote(String pollToken, String choiceToken) {
        Boolean isPollActive = pollRepository.findIfPollActive(pollToken);
        if (isPollActive) {
            pollChoiceRepository.addVote(choiceToken);
        }
        return pollToken;
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
        pollDetailsDto.setPollChoiceDetails(poll.getChoices().stream().map(pollChoice -> {
            PollChoiceDetailsDto pollChoiceDetailsDto = new PollChoiceDetailsDto();
            pollChoiceDetailsDto.setChoiceToken(pollChoice.getChoiceToken());
            pollChoiceDetailsDto.setChoiceName(pollChoice.getChoiceName());
            pollChoiceDetailsDto.setVoteCount(pollChoice.getVoteCount());
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
    public String changeLiveStatus(String pollToken, Boolean isLive) {
        this.pollRepository.changeLiveStatus(pollToken, isLive);
        return pollToken;
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
            polls.add(pollListDto);
        });
        return polls;
    }
}
