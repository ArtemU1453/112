package by.mchs.e112.analytics.repository.projection;

import java.time.Instant;
import java.util.UUID;

/**
 * Проекция агрегатов назначений по происшествию: время первого наряда и число нарядов (JPQL).
 */
public interface DispatchAggregateProjection {

    UUID getIncidentId();

    Instant getFirstDispatchAt();

    long getDispatchCount();
}
