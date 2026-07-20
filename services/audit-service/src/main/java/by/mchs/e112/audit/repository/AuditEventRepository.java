package by.mchs.e112.audit.repository;

import by.mchs.e112.audit.domain.AuditEvent;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AuditEventRepository extends JpaRepository<AuditEvent, UUID>,
    JpaSpecificationExecutor<AuditEvent> {
}
