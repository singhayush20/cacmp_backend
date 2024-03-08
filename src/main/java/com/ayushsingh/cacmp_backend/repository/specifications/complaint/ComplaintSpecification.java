package com.ayushsingh.cacmp_backend.repository.specifications.complaint;

import com.ayushsingh.cacmp_backend.models.entities.Complaint;
import com.ayushsingh.cacmp_backend.repository.filterDto.ComplaintFilter;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ComplaintSpecification {

    public static Specification<Complaint> filterComplaints(ComplaintFilter filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getStatus() != null) {
                predicates.add(criteriaBuilder.equal(root.get("complaintStatus"), filter.getStatus()));
            }
            if (filter.getPriority() != null) {
                predicates.add(criteriaBuilder.equal(root.get("complaintPriority"), filter.getPriority()));
            }
            if (filter.getPincode() != null) {
                predicates.add(criteriaBuilder.equal(root.get("complaintLocation").get("pincode"), filter.getPincode()));
            }
            if (filter.getWardNo() != null) {
                predicates.add(criteriaBuilder.equal(root.get("complaintLocation").get("wardNo"), filter.getWardNo()));
            }
            if(filter.getCategoryToken() != null){
                predicates.add(criteriaBuilder.equal(root.get("category").get("categoryToken"), filter.getCategoryToken()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}