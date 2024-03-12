package com.ayushsingh.cacmp_backend.services.serviceimpl;

import com.ayushsingh.cacmp_backend.models.constants.FeedbackRating;
import com.ayushsingh.cacmp_backend.models.dtos.complaintFeedbackDtos.ComplaintFeedbackDto;
import com.ayushsingh.cacmp_backend.models.entities.Complaint;
import com.ayushsingh.cacmp_backend.models.entities.ComplaintFeedback;
import com.ayushsingh.cacmp_backend.repository.entities.ComplaintFeedbackRepository;
import com.ayushsingh.cacmp_backend.repository.entities.ComplaintRepository;
import com.ayushsingh.cacmp_backend.services.ComplaintFeedbackService;
import com.ayushsingh.cacmp_backend.util.exceptionUtil.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ComplaintFeedbackServiceImpl implements ComplaintFeedbackService {

    private final ComplaintRepository complaintRepository;
    private final ComplaintFeedbackRepository complaintFeedbackRepository;
    @Override
    public String saveFeedback(ComplaintFeedbackDto complaintFeedbackDto) {
        Optional<Complaint> complaintOptional=complaintRepository.findByComplaintToken(complaintFeedbackDto.getComplaintToken());
        if(complaintOptional.isEmpty()){
            throw new ApiException("Complaint with token: "+complaintFeedbackDto.getComplaintToken()+" does not exist");
        }
        Optional<ComplaintFeedback> complaintFeedbackOptional=complaintFeedbackRepository.findByComplaintId(complaintFeedbackDto.getComplaintToken());
        ComplaintFeedback complaintFeedback = complaintFeedbackOptional.orElseGet(ComplaintFeedback::new);
            Complaint complaint=complaintOptional.get();
            complaintFeedback.setComplaint(complaint);
            if(complaintFeedbackDto.getFeedbackDescription()!=null){
            complaintFeedback.setFeedbackDescription(complaintFeedbackDto.getFeedbackDescription());
            }
            complaintFeedback.setFeedbackRating(FeedbackRating.fromValue(complaintFeedbackDto.getFeedbackRating()));
           ComplaintFeedback feedback= complaintFeedbackRepository.save(complaintFeedback);
           return feedback.getFeedbackToken();

    }

    @Override
    public ComplaintFeedbackDto getFeedbackForComplaint(String complaintToken) {
            Optional<ComplaintFeedback> complaintFeedbackOptional=complaintFeedbackRepository.findByComplaintId(complaintToken);
            if(complaintFeedbackOptional.isPresent()){
                ComplaintFeedbackDto complaintFeedbackDto=new ComplaintFeedbackDto();
                complaintFeedbackDto.setComplaintToken(complaintToken);
                complaintFeedbackDto.setFeedbackDescription(complaintFeedbackOptional.get().getFeedbackDescription());
                complaintFeedbackDto.setFeedbackRating(complaintFeedbackOptional.get().getFeedbackRating().getValue());
                return complaintFeedbackDto;
            }
            throw new ApiException("Feedback not present!");
    }
}
