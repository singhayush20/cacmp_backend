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

            // Add filter predicates based on the filter criteria
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

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}