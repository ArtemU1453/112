package by.mchs.e112.analytics.repository;

import by.mchs.e112.analytics.domain.AnalyticsDispatch;
import by.mchs.e112.analytics.repository.projection.DispatchAggregateProjection;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AnalyticsDispatchRepository extends JpaRepository<AnalyticsDispatch, UUID> {

    @Query("""
        SELECT d.incidentId AS incidentId,
               MIN(d.dispatchedAt) AS firstDispatchAt,
               COUNT(d) AS dispatchCount
        FROM AnalyticsDispatch d
        WHERE d.incidentId IN :incidentIds
        GROUP BY d.incidentId
        """)
    List<DispatchAggregateProjection> aggregateByIncidentIds(
        @Param("incidentIds") Collection<UUID> incidentIds);
}
