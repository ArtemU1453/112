package by.mchs.e112.incident.repository;

import by.mchs.e112.incident.domain.Incident;
import by.mchs.e112.incident.dto.IncidentFilter;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

public final class IncidentSpecifications {

    private IncidentSpecifications() {
    }

    public static Specification<Incident> byFilter(IncidentFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (filter.status() != null) {
                predicates.add(cb.equal(root.get("status"), filter.status()));
            }
            if (filter.type() != null) {
                predicates.add(cb.equal(root.get("type"), filter.type()));
            }
            if (filter.priority() != null) {
                predicates.add(cb.equal(root.get("priority"), filter.priority()));
            }
            if (filter.addressContains() != null && !filter.addressContains().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("address")),
                    "%" + filter.addressContains().toLowerCase() + "%"));
            }
            if (filter.createdFrom() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("createdAt"), filter.createdFrom()));
            }
            if (filter.createdTo() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("createdAt"), filter.createdTo()));
            }
            return cb.and(predicates.toArray(Predicate[]::new));
        };
    }
}
