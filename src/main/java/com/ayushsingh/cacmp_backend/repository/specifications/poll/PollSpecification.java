package com.ayushsingh.cacmp_backend.repository.specifications.poll;

import com.ayushsingh.cacmp_backend.models.entities.Complaint;
import com.ayushsingh.cacmp_backend.models.entities.Poll;
import com.ayushsingh.cacmp_backend.repository.filterDto.ComplaintFilter;
import com.ayushsingh.cacmp_backend.repository.filterDto.PollFilter;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class PollSpecification {

    public static Specification<Poll> filterPolls(PollFilter filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getDeptToken()!= null) {
                predicates.add(criteriaBuilder.equal(root.get("department").get("deptToken"), filter.getDeptToken()));
            }
            if (filter.getIsLive() != null) {
                predicates.add(criteriaBuilder.equal(root.get("isLive"), filter.getIsLive()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
