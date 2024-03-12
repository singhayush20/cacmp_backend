package com.ayushsingh.cacmp_backend.services.serviceimpl;

import com.ayushsingh.cacmp_backend.models.constants.ComplaintPriority;
import com.ayushsingh.cacmp_backend.models.constants.ComplaintStatus;
import com.ayushsingh.cacmp_backend.models.dtos.complaintDtos.ComplaintCreateDto;
import com.ayushsingh.cacmp_backend.models.dtos.complaintDtos.ComplaintListDetailsDto;
import com.ayushsingh.cacmp_backend.models.dtos.complaintDtos.ComplaintStatusDto;
import com.ayushsingh.cacmp_backend.models.entities.Category;
import com.ayushsingh.cacmp_backend.models.entities.Complaint;
import com.ayushsingh.cacmp_backend.models.entities.ComplaintImage;
import com.ayushsingh.cacmp_backend.models.entities.ComplaintLocation;
import com.ayushsingh.cacmp_backend.models.entities.Consumer;
import com.ayushsingh.cacmp_backend.models.projections.complaint.ComplaintDetailsProjection;
import com.ayushsingh.cacmp_backend.models.projections.complaint.ComplaintListDetailsProjection;
import com.ayushsingh.cacmp_backend.repository.entities.CategoryRepository;
import com.ayushsingh.cacmp_backend.repository.entities.ComplaintImageRepository;
import com.ayushsingh.cacmp_backend.repository.entities.ComplaintLocationRepository;
import com.ayushsingh.cacmp_backend.repository.entities.ComplaintRepository;
import com.ayushsingh.cacmp_backend.repository.entities.ConsumerRepository;
import com.ayushsingh.cacmp_backend.repository.filterDto.ComplaintFilter;
import com.ayushsingh.cacmp_backend.repository.specifications.complaint.ComplaintSpecification;
import com.ayushsingh.cacmp_backend.services.ComplaintService;
import com.ayushsingh.cacmp_backend.util.exceptionUtil.ApiException;
import com.ayushsingh.cacmp_backend.util.imageUtil.ImageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ComplaintServiceImpl implements ComplaintService {

    private final ComplaintRepository complaintRepository;
    private final CategoryRepository categoryRepository;
    private final ComplaintImageRepository complaintImageRepository;
    private final ImageService imageService;
    private final ConsumerRepository consumerRepository;
    private final ComplaintLocationRepository complaintLocationRepository;

    @Transactional
    @Override
    public String createNewComplaint(ComplaintCreateDto complaintCreateDto) {

        String categoryToken = complaintCreateDto.getCategoryToken();
        Optional<Category> categoryOptional = categoryRepository.findByCategoryToken(categoryToken);
        if (categoryOptional.isEmpty()) {
            throw new RuntimeException("Category not found");
        }

        // load consumer
        Optional<Consumer> consumerOptional = consumerRepository
                .findByConsumerToken(complaintCreateDto.getConsumerToken());
        if (consumerOptional.isEmpty()) {
            throw new RuntimeException("Consumer not found");
        }

        Complaint complaint = new Complaint();
        complaint.setComplaintDescription(complaintCreateDto.getComplaintDescription());
        complaint.setComplaintSubject(complaintCreateDto.getComplaintSubject());
        complaint.setComplaintStatus(ComplaintStatus.OPEN);
        complaint.setComplaintPriority(ComplaintPriority.fromValue(complaintCreateDto.getComplaintPriority()));
        complaint.setCategory(categoryOptional.get());
        complaint.setConsumer(consumerOptional.get());
        ComplaintLocation complaintLocation = saveComplaintLocation(complaintCreateDto);
        complaint.setComplaintLocation(complaintLocation);
        complaint = complaintRepository.save(complaint);

        return complaint.getComplaintToken();
    }

    private ComplaintLocation saveComplaintLocation(ComplaintCreateDto complaintCreateDto) {
        ComplaintLocation complaintLocation = new ComplaintLocation();
        complaintLocation.setAddress(complaintCreateDto.getAddress());
        complaintLocation.setPincode(complaintCreateDto.getPincode());
        complaintLocation.setContactNo(complaintCreateDto.getContactNo());
        complaintLocation.setWardNo(complaintCreateDto.getWardNo());
        complaintLocationRepository.save(complaintLocation);
        return complaintLocation;
    }

    @Transactional
    @Override
    public String changeStatus(ComplaintStatusDto complaintDto) {
        String status = complaintDto.getComplaintStatus();
        ComplaintStatus newStatus = ComplaintStatus.fromValue(status);
        this.complaintRepository.updateComplaintStatus(newStatus, complaintDto.getComplaintToken());
        return complaintDto.getComplaintToken();
    }

    @Override
    public List<ComplaintListDetailsProjection> searchByPincode(Long pincode) {
        return complaintRepository.findAllByPincode(pincode);
    }

    @Override
    public List<ComplaintListDetailsProjection> searchByWardNo(String wardNo) {
        return complaintRepository.findAllByWardNo(wardNo);
    }

    @Override
    public ComplaintDetailsProjection getComplaintDetails(String complaintToken) {
        return complaintRepository.getComplaintDetails(complaintToken);
    }

    @Override
    public List<String> getComplaintImages(String token) {
        return complaintImageRepository.getComplaintImages(token);
    }



    @Override
    public List<ComplaintListDetailsProjection> getAllComplaintsByStatus(String status) {
        ComplaintStatus complaintStatus = ComplaintStatus.fromValue(status);
        return complaintRepository.getAllComplaintsByStatus(complaintStatus);
    }

    @Override
    public List<ComplaintListDetailsProjection> getAllComplaintsByPriority(String priority) {
        ComplaintPriority complaintPriority = ComplaintPriority.fromValue(priority);
        return complaintRepository.getAllComplaintsByPriority(complaintPriority);
    }


    private void saveImages(MultipartFile[] images, Complaint complaint) {
        for (MultipartFile image : images) {
            Map<String, Object> uploadResult = imageService.upload(image);
            ComplaintImage complaintImage = new ComplaintImage();
            complaintImage.setImageUrl((String) uploadResult.get("secure_url"));
            complaintImage.setPublicId((String) uploadResult.get("public_id"));
            complaintImage.setAssetId((String) uploadResult.get("asset_id"));
            complaintImage.setSignature((String) uploadResult.get("signature"));
            complaintImage.setComplaint(complaint);
            complaintImageRepository.save(complaintImage);
        }
    }


    public List<ComplaintListDetailsDto> getFilteredComplaints(ComplaintFilter complaintFilter, Sort sort) {
        Specification<Complaint> spec = ComplaintSpecification.filterComplaints(complaintFilter);
        List<Complaint> complaints = complaintRepository.findAll(spec, sort);

        return complaints.stream().map(complaint -> {

            ComplaintListDetailsDto complaintListDetailsDto = new ComplaintListDetailsDto();
            complaintListDetailsDto.setComplaintToken(complaint.getComplaintToken());
            complaintListDetailsDto.setComplaintSubject(complaint.getComplaintSubject());
            complaintListDetailsDto.setComplaintStatus(complaint.getComplaintStatus());
            complaintListDetailsDto.setComplaintDescription(complaint.getComplaintDescription());
            complaintListDetailsDto.setComplaintPriority(complaint.getComplaintPriority());
            return complaintListDetailsDto;
        }).toList();
    }

    @Transactional
    @Override
    public List<ComplaintListDetailsDto> getComplaintsForConsumer(ComplaintFilter filter, Sort sort) {
        Specification<Complaint> spec = ComplaintSpecification.filterComplaints(filter);
        List<Complaint> complaints = complaintRepository.findAll(spec, sort);
        return complaints.stream().map(complaint -> {

            ComplaintListDetailsDto complaintListDetailsDto = new ComplaintListDetailsDto();
            complaintListDetailsDto.setComplaintToken(complaint.getComplaintToken());
            complaintListDetailsDto.setComplaintSubject(complaint.getComplaintSubject());
            complaintListDetailsDto.setComplaintStatus(complaint.getComplaintStatus());
            complaintListDetailsDto.setComplaintDescription(complaint.getComplaintDescription());
            complaintListDetailsDto.setComplaintPriority(complaint.getComplaintPriority());
            return complaintListDetailsDto;
        }).toList();
    }

    @Override
    public String saveComplaintImages(String token, MultipartFile[] images) {
        Complaint complaint = this.complaintRepository.findByComplaintToken(token).orElseThrow(() -> new ApiException("Complaint with token " + token + " not found"));
        saveImages(images, complaint);
        return token;
    }


}
