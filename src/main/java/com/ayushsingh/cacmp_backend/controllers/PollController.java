package com.ayushsingh.cacmp_backend.controllers;

import com.ayushsingh.cacmp_backend.models.dtos.pollDto.*;
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
    @PutMapping("/status")
    public ResponseEntity<ApiResponse<String>> changeLiveStatus (@RequestBody PollStatusDto pollStatusDto) {
        return ResponseEntity.ok(new ApiResponse<>(pollService.changeLiveStatus(pollStatusDto)));
    }

    @PreAuthorize("hasAnyRole('ROLE_RESIDENT','ROLE_NON_RESIDENT')")
    @GetMapping("/poll-list")
    public ResponseEntity<ApiResponse<List<PollListProjection>>> getPollList () {
        return ResponseEntity.ok(new ApiResponse<>(pollService.getPollList()));
    }

    @PreAuthorize("hasAnyRole('ROLE_RESIDENT','ROLE_DEPARTMENT')")
    @GetMapping("/details")
    public ResponseEntity<ApiResponse<PollDetailsDto>> getPollDetails (@RequestParam("token") String pollToken) {
        return ResponseEntity.ok(new ApiResponse<>(pollService.getPollDetails(pollToken)));
    }

    @PreAuthorize("hasRole('ROLE_RESIDENT')")
    @PutMapping("/cast-vote")
    public ResponseEntity<ApiResponse<String>> castVote (@RequestBody VoteDto voteDto) {
        return ResponseEntity.ok(new ApiResponse<>(pollService.castVote(voteDto)));
    }

    @PreAuthorize("hasRole('ROLE_DEPARTMENT')")
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<PollListDto>>> getPollsByDepartment (@RequestParam("token") String deptToken
            , @RequestParam(value="isLive",required = false) Boolean isLive, @RequestParam(value = "sortBy",required = false) String sortBy) {
        PollFilter pollFilter = new PollFilter();
        pollFilter.setDeptToken(deptToken);
        pollFilter.setIsLive(isLive);
        Sort sort = sortBy != null ? Sort.by(sortBy) : Sort.by("createdAt");
        sort = sort.descending();
        return ResponseEntity.ok(new ApiResponse<>(pollService.listPollsByDept(pollFilter, sort)));
    }

    @DeleteMapping("/{pollToken}")
    public ResponseEntity<ApiResponse<String>> deletePoll (@PathVariable String pollToken) {
        return ResponseEntity.ok(new ApiResponse<>(pollService.deletePoll(pollToken)));
    }
}
