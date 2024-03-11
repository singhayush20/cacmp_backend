package com.ayushsingh.cacmp_backend.services.serviceimpl;

import com.ayushsingh.cacmp_backend.models.constants.FeedbackRating;
import com.ayushsingh.cacmp_backend.models.dtos.complaintFeedbackDtos.ComplaintFeedbackDto;
import com.ayushsingh.cacmp_backend.models.entities.Complaint;
import com.ayushsingh.cacmp_backend.models.entities.ComplaintFeedback;
import com.ayushsingh.cacmp_backend.models.projections.feedbackComplaint.ComplaintFeedbackProjection;
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

        if(complaintOptional.isPresent()){
            Complaint complaint=complaintOptional.get();
            ComplaintFeedback complaintFeedback=new ComplaintFeedback();
            complaintFeedback.setComplaint(complaint);
            if(complaintFeedbackDto.getFeedbackDescription()!=null){
            complaintFeedback.setFeedbackDescription(complaintFeedbackDto.getFeedbackDescription());
            }
            complaintFeedback.setFeedbackRating(FeedbackRating.fromValue(complaintFeedbackDto.getFeedbackRating()));
           ComplaintFeedback feedback= complaintFeedbackRepository.save(complaintFeedback);
           return feedback.getFeedbackToken();
        }
        throw new ApiException("Complaint with token: "+complaintFeedbackDto.getComplaintToken()+" does not exist");
    }

    @Override
    public ComplaintFeedbackProjection getFeedbackForComplaint(String complaintToken) {
            return complaintFeedbackRepository.findByComplaint(complaintToken);
    }
}
