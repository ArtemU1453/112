package by.mchs.e112.analytics.repository;

import by.mchs.e112.analytics.domain.AnalyticsIncident;
import by.mchs.e112.analytics.repository.projection.CategoryCountProjection;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AnalyticsIncidentRepository
    extends JpaRepository<AnalyticsIncident, UUID>, JpaSpecificationExecutor<AnalyticsIncident> {

    long countByCreatedAtGreaterThanEqualAndCreatedAtLessThan(Instant from, Instant to);

    long countByStatusInAndCreatedAtGreaterThanEqualAndCreatedAtLessThan(
        Collection<String> statuses, Instant from, Instant to);

    @Query("""
        SELECT i.type AS category, COUNT(i) AS count
        FROM AnalyticsIncident i
        WHERE i.createdAt >= :from AND i.createdAt < :to
        GROUP BY i.type
        ORDER BY COUNT(i) DESC
        """)
    List<CategoryCountProjection> countByType(@Param("from") Instant from, @Param("to") Instant to);

    @Query("""
        SELECT i.priority AS category, COUNT(i) AS count
        FROM AnalyticsIncident i
        WHERE i.createdAt >= :from AND i.createdAt < :to
        GROUP BY i.priority
        ORDER BY COUNT(i) DESC
        """)
    List<CategoryCountProjection> countByPriority(@Param("from") Instant from, @Param("to") Instant to);

    @Query("""
        SELECT i.status AS category, COUNT(i) AS count
        FROM AnalyticsIncident i
        WHERE i.createdAt >= :from AND i.createdAt < :to
        GROUP BY i.status
        ORDER BY COUNT(i) DESC
        """)
    List<CategoryCountProjection> countByStatus(@Param("from") Instant from, @Param("to") Instant to);

    @Query("""
        SELECT COUNT(DISTINCT d.incidentId)
        FROM AnalyticsDispatch d
        WHERE d.incidentId IN (
            SELECT i.incidentId FROM AnalyticsIncident i
            WHERE i.createdAt >= :from AND i.createdAt < :to)
        """)
    long countDispatchedIncidents(@Param("from") Instant from, @Param("to") Instant to);

    /**
     * Среднее время (в секундах) от регистрации происшествия до первого наряда за период.
     * Возвращает null, если за период не было назначений. Нативный запрос — интервальная арифметика
     * PostgreSQL; параметр гранулярности отсутствует, инъекция невозможна.
     */
    @Query(value = """
        SELECT AVG(EXTRACT(EPOCH FROM (fd.first_at - i.created_at)))
        FROM analytics_incident i
        JOIN (SELECT incident_id, MIN(dispatched_at) AS first_at
              FROM analytics_dispatch GROUP BY incident_id) fd
          ON fd.incident_id = i.incident_id
        WHERE i.created_at >= :from AND i.created_at < :to
        """, nativeQuery = true)
    Double avgFirstDispatchSeconds(@Param("from") Instant from, @Param("to") Instant to);

    /**
     * Временной ряд числа происшествий по бакетам. Гранулярность передаётся как проверенное значение
     * enum ({@code hour|day|week|month}) — связанный параметр date_trunc, безопасно.
     */
    @Query(value = """
        SELECT date_trunc(:granularity, created_at) AS bucket_start, COUNT(*) AS bucket_count
        FROM analytics_incident
        WHERE created_at >= :from AND created_at < :to
        GROUP BY 1
        ORDER BY 1
        """, nativeQuery = true)
    List<Object[]> timeSeries(@Param("granularity") String granularity,
                              @Param("from") Instant from, @Param("to") Instant to);

    List<AnalyticsIncident> findByIncidentIdIn(Collection<UUID> incidentIds);
}
