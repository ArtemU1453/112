package by.mchs.e112.dispatch.repository;

import by.mchs.e112.dispatch.domain.Assignment;
import by.mchs.e112.dispatch.domain.AssignmentStatus;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignmentRepository extends JpaRepository<Assignment, UUID> {

    List<Assignment> findByIncidentId(UUID incidentId);

    List<Assignment> findByIncidentIdAndStatus(UUID incidentId, AssignmentStatus status);

    List<Assignment> findByUnitIdAndStatus(UUID unitId, AssignmentStatus status);
}
