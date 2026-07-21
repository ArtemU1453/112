package by.mchs.e112.analytics.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;

/**
 * Аналитическая проекция карточки происшествия (read model). Наполняется из событий
 * incident.created / incident.updated. Не является источником истины домена — только витрина.
 */
@Entity
@Table(name = "analytics_incident")
public class AnalyticsIncident {

    @Id
    @Column(name = "incident_id", nullable = false, updatable = false)
    private UUID incidentId;

    @Column(name = "number")
    private String number;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "priority", nullable = false)
    private String priority;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "casualties_count", nullable = false)
    private int casualtiesCount;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "resolved_at")
    private Instant resolvedAt;

    @Column(name = "closed_at")
    private Instant closedAt;

    protected AnalyticsIncident() {
    }

    public AnalyticsIncident(UUID incidentId, String number, String type, String priority,
                             String status, int casualtiesCount, Double latitude, Double longitude,
                             Instant createdAt) {
        this.incidentId = incidentId;
        this.number = number;
        this.type = type;
        this.priority = priority;
        this.status = status;
        this.casualtiesCount = casualtiesCount;
        this.latitude = latitude;
        this.longitude = longitude;
        this.createdAt = createdAt;
    }

    public UUID getIncidentId() {
        return incidentId;
    }

    public String getNumber() {
        return number;
    }

    public String getType() {
        return type;
    }

    public String getPriority() {
        return priority;
    }

    public String getStatus() {
        return status;
    }

    public int getCasualtiesCount() {
        return casualtiesCount;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Instant getResolvedAt() {
        return resolvedAt;
    }

    public Instant getClosedAt() {
        return closedAt;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCasualtiesCount(int casualtiesCount) {
        this.casualtiesCount = casualtiesCount;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setResolvedAt(Instant resolvedAt) {
        this.resolvedAt = resolvedAt;
    }

    public void setClosedAt(Instant closedAt) {
        this.closedAt = closedAt;
    }
}
