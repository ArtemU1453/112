package by.mchs.e112.audit.service;

import by.mchs.e112.audit.domain.AuditEvent;
import by.mchs.e112.audit.dto.AuditEventResponse;
import by.mchs.e112.audit.dto.AuditFilter;
import by.mchs.e112.audit.repository.AuditEventRepository;
import by.mchs.e112.audit.repository.AuditSpecifications;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuditService {

    private final AuditEventRepository repository;

    public AuditService(AuditEventRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void record(String service, String action, String entityType, String entityId,
                       String actor, Map<String, Object> details, Instant occurredAt) {
        AuditEvent event = new AuditEvent(UUID.randomUUID(), service, action, entityType,
            entityId, actor, details, occurredAt == null ? Instant.now() : occurredAt);
        repository.save(event);
    }

    @Transactional(readOnly = true)
    public Page<AuditEventResponse> search(AuditFilter filter, Pageable pageable) {
        return repository.findAll(AuditSpecifications.byFilter(filter), pageable).map(this::toResponse);
    }

    private AuditEventResponse toResponse(AuditEvent event) {
        return new AuditEventResponse(
            event.getId(), event.getService(), event.getAction(), event.getEntityType(),
            event.getEntityId(), event.getActor(), event.getDetails(),
            event.getOccurredAt(), event.getRecordedAt());
    }
}
