package com.ayushsingh.cacmp_backend.services.serviceimpl;

import com.ayushsingh.cacmp_backend.models.constants.ComplaintPriority;
import com.ayushsingh.cacmp_backend.models.constants.ComplaintStatus;
import com.ayushsingh.cacmp_backend.models.dtos.complaintDtos.ComplaintCreateDto;
import com.ayushsingh.cacmp_backend.models.dtos.complaintDtos.ComplaintStatusDto;
import com.ayushsingh.cacmp_backend.models.entities.Category;
import com.ayushsingh.cacmp_backend.models.entities.Complaint;
import com.ayushsingh.cacmp_backend.models.entities.ComplaintImage;
import com.ayushsingh.cacmp_backend.models.entities.ComplaintLocation;
import com.ayushsingh.cacmp_backend.models.entities.Consumer;
import com.ayushsingh.cacmp_backend.repository.entities.CategoryRepository;
import com.ayushsingh.cacmp_backend.repository.entities.ComplaintImageRepository;
import com.ayushsingh.cacmp_backend.repository.entities.ComplaintLocationRepository;
import com.ayushsingh.cacmp_backend.repository.entities.ComplaintRepository;
import com.ayushsingh.cacmp_backend.repository.entities.ConsumerRepository;
import com.ayushsingh.cacmp_backend.services.ComplaintService;
import com.ayushsingh.cacmp_backend.util.imageUtil.ImageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    public String createNewComplaint(ComplaintCreateDto complaintCreateDto, MultipartFile[] images) {
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
        complaint.setAddress(complaintLocation);
        complaint = complaintRepository.save(complaint);
        saveImages(images, complaint);

        return complaint.getComplaintToken();
    }

    private ComplaintLocation saveComplaintLocation(ComplaintCreateDto complaintCreateDto) {
        ComplaintLocation complaintLocation = new ComplaintLocation();
        complaintLocation.setAddress(complaintCreateDto.getAddress());
        complaintLocation.setPincode(complaintCreateDto.getPincode());
        complaintLocation.setWardNo(complaintCreateDto.getWardNo());
        complaintLocationRepository.save(complaintLocation);
        return complaintLocation;
    }

    @Override
    public String changeStatus(ComplaintStatusDto complaintDto) {
        String status = complaintDto.getComplaintStatus();
        ComplaintStatus newStatus = ComplaintStatus.fromValue(status);
        this.complaintRepository.updateComplaintStatus(newStatus, complaintDto.getComplaintToken());
        return complaintDto.getComplaintToken();
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

}
