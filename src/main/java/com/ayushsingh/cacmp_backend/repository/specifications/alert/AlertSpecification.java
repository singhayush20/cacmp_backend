package com.ayushsingh.cacmp_backend.repository.specifications.alert;

import com.ayushsingh.cacmp_backend.models.entities.Alert;
import com.ayushsingh.cacmp_backend.repository.filterDto.AlertFilter;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class AlertSpecification {

    public static Specification<Alert> filterAlerts (AlertFilter filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();


            if (filter.getAlertToken() != null) {
                predicates.add(criteriaBuilder.equal(root.get("alertToken"), filter.getAlertToken()));
            }
            if (filter.getPublishStatus() != null) {
                predicates.add(criteriaBuilder.equal(root.get("publishStatus"), filter.getPublishStatus()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
