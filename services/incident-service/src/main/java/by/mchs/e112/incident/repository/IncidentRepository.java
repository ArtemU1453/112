package by.mchs.e112.incident.repository;

import by.mchs.e112.incident.domain.Incident;
import by.mchs.e112.incident.domain.IncidentStatus;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IncidentRepository extends JpaRepository<Incident, UUID>, JpaSpecificationExecutor<Incident> {

    Optional<Incident> findByNumber(String number);

    List<Incident> findByStatusIn(List<IncidentStatus> statuses);

    long countByStatusIn(List<IncidentStatus> statuses);

    long countByCreatedAtAfter(Instant from);

    @Query("select i.status as k, count(i) as v from Incident i group by i.status")
    List<CountByKey> countGroupByStatus();

    @Query("select i.type as k, count(i) as v from Incident i where i.createdAt >= :from group by i.type")
    List<CountByKey> countGroupByType(@Param("from") Instant from);

    @Query("select i.priority as k, count(i) as v from Incident i where i.createdAt >= :from group by i.priority")
    List<CountByKey> countGroupByPriority(@Param("from") Instant from);

    interface CountByKey {
        Object getK();
        long getV();
    }
}
