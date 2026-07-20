package by.mchs.e112.dispatch.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;

/**
 * Назначение наряда на происшествие (связь Unit ↔ Incident).
 */
@Entity
@Table(name = "assignment")
public class Assignment {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "incident_id", nullable = false)
    private UUID incidentId;

    @Column(name = "unit_id", nullable = false)
    private UUID unitId;

    @Column(name = "unit_call_sign", nullable = false, length = 30)
    private String unitCallSign;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private AssignmentStatus status;

    @Column(name = "assigned_by", nullable = false, length = 64)
    private String assignedBy;

    @Column(name = "distance_km")
    private Double distanceKm;

    @Column(name = "assigned_at", nullable = false)
    private Instant assignedAt;

    @Column(name = "completed_at")
    private Instant completedAt;

    protected Assignment() {
    }

    public Assignment(UUID id, UUID incidentId, Unit unit, String assignedBy, Double distanceKm) {
        this.id = id;
        this.incidentId = incidentId;
        this.unitId = unit.getId();
        this.unitCallSign = unit.getCallSign();
        this.status = AssignmentStatus.ACTIVE;
        this.assignedBy = assignedBy;
        this.distanceKm = distanceKm;
        this.assignedAt = Instant.now();
    }

    public void complete() {
        this.status = AssignmentStatus.COMPLETED;
        this.completedAt = Instant.now();
    }

    public void recall() {
        this.status = AssignmentStatus.RECALLED;
        this.completedAt = Instant.now();
    }

    public boolean isActive() {
        return status == AssignmentStatus.ACTIVE;
    }

    public UUID getId() { return id; }
    public UUID getIncidentId() { return incidentId; }
    public UUID getUnitId() { return unitId; }
    public String getUnitCallSign() { return unitCallSign; }
    public AssignmentStatus getStatus() { return status; }
    public String getAssignedBy() { return assignedBy; }
    public Double getDistanceKm() { return distanceKm; }
    public Instant getAssignedAt() { return assignedAt; }
    public Instant getCompletedAt() { return completedAt; }
}
