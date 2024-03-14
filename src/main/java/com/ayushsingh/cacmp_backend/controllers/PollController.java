package com.ayushsingh.cacmp_backend.controllers;

import com.ayushsingh.cacmp_backend.models.dtos.pollDto.PollCreateDto;
import com.ayushsingh.cacmp_backend.models.dtos.pollDto.PollDetailsDto;
import com.ayushsingh.cacmp_backend.models.dtos.pollDto.PollListDto;
import com.ayushsingh.cacmp_backend.models.projections.poll.PollListProjection;
import com.ayushsingh.cacmp_backend.repository.filterDto.PollFilter;
import com.ayushsingh.cacmp_backend.services.PollService;
import com.ayushsingh.cacmp_backend.util.responseUtil.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/poll")
@RequiredArgsConstructor
public class PollController {

    private final PollService pollService;

    @PreAuthorize("hasRole('ROLE_DEPARTMENT')")
    @PostMapping("/new")
    public ResponseEntity<ApiResponse<String>> createNewPOll (@RequestBody PollCreateDto pollCreateDto) {
        return new ResponseEntity<>(new ApiResponse<>(pollService.createPoll(pollCreateDto)),
                HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_DEPARTMENT')")
    @PutMapping("/poll-status")
    public ResponseEntity<ApiResponse<String>> changeLiveStatus (@RequestParam String pollToken,
                                                                 @RequestParam Boolean isLive) {
        return ResponseEntity.ok(new ApiResponse<>(pollService.changeLiveStatus(pollToken,
                isLive)));
    }

    @PreAuthorize("hasAnyRole('ROLE_RESIDENT','ROLE_NON_RESIDENT')")
    @GetMapping("/poll-list")
    public ResponseEntity<ApiResponse<List<PollListProjection>>> getPollList () {
        return ResponseEntity.ok(new ApiResponse<>(pollService.getPollList()));
    }

    @PreAuthorize("hasAnyRole('ROLE_RESIDENT','ROLE_DEPARTMENT')")
    @GetMapping("/poll-details")
    public ResponseEntity<ApiResponse<PollDetailsDto>> getPollDetails (@RequestParam String pollToken) {
        return ResponseEntity.ok(new ApiResponse<>(pollService.getPollDetails(pollToken)));
    }

    @PreAuthorize("hasRole('ROLE_RESIDENT')")
    @PutMapping("/cast-vote")
    public ResponseEntity<ApiResponse<String>> castVote (@RequestParam String pollToken,
                                                         @RequestParam String choiceToken) {
        return ResponseEntity.ok(new ApiResponse<>(pollService.castVote(pollToken, choiceToken)));
    }

    @PreAuthorize("hasRole('ROLE_DEPARTMENT')")
    @GetMapping("/list-polls-by-dept")
    public ResponseEntity<List<PollListDto>> getPollsByDepartment (@RequestParam String deptToken
            , @RequestParam(required = false) Boolean isLive, @RequestParam(required = false) String sortBy) {
        PollFilter pollFilter = new PollFilter();
        pollFilter.setDeptToken(deptToken);
        pollFilter.setIsLive(isLive);
        Sort sort = sortBy != null ? Sort.by(sortBy) : Sort.by("createdAt");
        sort = sort.descending();
        return ResponseEntity.ok(pollService.listPollsByDept(pollFilter, sort));
    }
}
