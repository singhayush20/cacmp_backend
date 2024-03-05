package com.ayushsingh.cacmp_backend.services.serviceimpl;

import com.ayushsingh.cacmp_backend.models.constants.ComplaintPriority;
import com.ayushsingh.cacmp_backend.models.constants.ComplaintStatus;
import com.ayushsingh.cacmp_backend.models.dtos.complaintDtos.ComplaintCreateDto;
import com.ayushsingh.cacmp_backend.models.entities.Category;
import com.ayushsingh.cacmp_backend.models.entities.Complaint;
import com.ayushsingh.cacmp_backend.models.entities.ComplaintImage;
import com.ayushsingh.cacmp_backend.models.entities.Consumer;
import com.ayushsingh.cacmp_backend.repository.entities.CategoryRepository;
import com.ayushsingh.cacmp_backend.repository.entities.ComplaintImageRepository;
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
import java.util.Set;


@Service
@RequiredArgsConstructor
public class ComplaintServiceImpl implements ComplaintService {

    private final ComplaintRepository complaintRepository;
    private final CategoryRepository categoryRepository;
    private final ComplaintImageRepository complaintImageRepository;
    private final ImageService imageService;
    private final ConsumerRepository consumerRepository;
    @Transactional
    @Override
    public String createNewComplaint(ComplaintCreateDto complaintCreateDto, MultipartFile[] images) {
        String categoryToken=complaintCreateDto.getCategoryToken();
        Optional<Category> categoryOptional=categoryRepository.findByCategoryToken(categoryToken);
        if(categoryOptional.isEmpty()){
            throw new RuntimeException("Category not found");
        }

        //load consumer
        Optional<Consumer> consumerOptional=consumerRepository.findByConsumerToken(complaintCreateDto.getConsumerToken());
        if(consumerOptional.isEmpty()){
            throw new RuntimeException("Consumer not found");
        }

        Complaint complaint=new Complaint();
        complaint.setComplaintDescription(complaintCreateDto.getComplaintDescription());
        complaint.setComplaintSubject(complaintCreateDto.getComplaintSubject());
        complaint.setComplaintStatus(ComplaintStatus.OPEN);
        complaint.setComplaintPriority(ComplaintPriority.fromValue(complaintCreateDto.getComplaintPriority()));
        complaint.setCategory(categoryOptional.get());
        complaint.setConsumer(consumerOptional.get());
        complaint=complaintRepository.save(complaint);
        saveImages(images, complaint);

        return complaint.getComplaintToken();
    }

    private void saveImages(MultipartFile[] images, Complaint complaint) {
        for(MultipartFile image: images){
            Map<String,Object> uploadResult=imageService.upload(image);
            ComplaintImage complaintImage=new ComplaintImage();
            complaintImage.setImageUrl((String)uploadResult.get("secure_url"));
            complaintImage.setPublicId((String)uploadResult.get("public_id"));
            complaintImage.setAssetId((String)uploadResult.get("asset_id"));
            complaintImage.setSignature((String)uploadResult.get("signature"));
            complaintImage.setComplaint(complaint);
            complaintImageRepository.save(complaintImage);
        }
    }


}
