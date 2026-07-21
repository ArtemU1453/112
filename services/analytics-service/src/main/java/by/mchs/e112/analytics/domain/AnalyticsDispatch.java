package by.mchs.e112.analytics.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;

/**
 * Аналитическая проекция назначения наряда (read model) из события dispatch.assigned.
 * Первичный ключ — идентификатор назначения, что обеспечивает идемпотентность приёма событий.
 */
@Entity
@Table(name = "analytics_dispatch")
public class AnalyticsDispatch {

    @Id
    @Column(name = "assignment_id", nullable = false, updatable = false)
    private UUID assignmentId;

    @Column(name = "incident_id", nullable = false)
    private UUID incidentId;

    @Column(name = "unit_id", nullable = false)
    private UUID unitId;

    @Column(name = "unit_call_sign")
    private String unitCallSign;

    @Column(name = "distance_km")
    private Double distanceKm;

    @Column(name = "dispatched_at", nullable = false)
    private Instant dispatchedAt;

    protected AnalyticsDispatch() {
    }

    public AnalyticsDispatch(UUID assignmentId, UUID incidentId, UUID unitId, String unitCallSign,
                             Double distanceKm, Instant dispatchedAt) {
        this.assignmentId = assignmentId;
        this.incidentId = incidentId;
        this.unitId = unitId;
        this.unitCallSign = unitCallSign;
        this.distanceKm = distanceKm;
        this.dispatchedAt = dispatchedAt;
    }

    public UUID getAssignmentId() {
        return assignmentId;
    }

    public UUID getIncidentId() {
        return incidentId;
    }

    public UUID getUnitId() {
        return unitId;
    }

    public String getUnitCallSign() {
        return unitCallSign;
    }

    public Double getDistanceKm() {
        return distanceKm;
    }

    public Instant getDispatchedAt() {
        return dispatchedAt;
    }
}
