package by.mchs.e112.audit.repository;

import by.mchs.e112.audit.domain.AuditEvent;
import by.mchs.e112.audit.dto.AuditFilter;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

public final class AuditSpecifications {

    private AuditSpecifications() {
    }

    public static Specification<AuditEvent> byFilter(AuditFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (filter.service() != null) {
                predicates.add(cb.equal(root.get("service"), filter.service()));
            }
            if (filter.action() != null) {
                predicates.add(cb.equal(root.get("action"), filter.action()));
            }
            if (filter.entityType() != null) {
                predicates.add(cb.equal(root.get("entityType"), filter.entityType()));
            }
            if (filter.entityId() != null) {
                predicates.add(cb.equal(root.get("entityId"), filter.entityId()));
            }
            if (filter.actor() != null) {
                predicates.add(cb.equal(root.get("actor"), filter.actor()));
            }
            if (filter.from() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("occurredAt"), filter.from()));
            }
            if (filter.to() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("occurredAt"), filter.to()));
            }
            return cb.and(predicates.toArray(Predicate[]::new));
        };
    }
}
