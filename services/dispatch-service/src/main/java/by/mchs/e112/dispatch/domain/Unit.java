package by.mchs.e112.dispatch.domain;

import by.mchs.e112.dispatch.exception.IllegalUnitStatusTransitionException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.time.Instant;
import java.util.UUID;

/**
 * Подразделение (оперативная единица), способное выезжать на происшествия.
 */
@Entity
@Table(name = "unit")
public class Unit {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "call_sign", nullable = false, unique = true, length = 30)
    private String callSign;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 20)
    private UnitType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private UnitStatus status;

    @Column(name = "station_id", nullable = false)
    private UUID stationId;

    @Column(name = "station_name", nullable = false, length = 200)
    private String stationName;

    @Column(name = "crew_size", nullable = false)
    private int crewSize;

    @Column(name = "base_latitude", nullable = false)
    private double baseLatitude;

    @Column(name = "base_longitude", nullable = false)
    private double baseLongitude;

    @Column(name = "current_latitude")
    private Double currentLatitude;

    @Column(name = "current_longitude")
    private Double currentLongitude;

    @Column(name = "active_incident_id")
    private UUID activeIncidentId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Version
    @Column(name = "version", nullable = false)
    private long version;

    protected Unit() {
    }

    public Unit(UUID id, String callSign, UnitType type, UUID stationId, String stationName,
                int crewSize, double baseLatitude, double baseLongitude) {
        this.id = id;
        this.callSign = callSign;
        this.type = type;
        this.status = UnitStatus.AVAILABLE;
        this.stationId = stationId;
        this.stationName = stationName;
        this.crewSize = crewSize;
        this.baseLatitude = baseLatitude;
        this.baseLongitude = baseLongitude;
        this.currentLatitude = baseLatitude;
        this.currentLongitude = baseLongitude;
        this.createdAt = Instant.now();
        this.updatedAt = this.createdAt;
    }

    public void changeStatus(UnitStatus target) {
        if (!status.canTransitionTo(target)) {
            throw new IllegalUnitStatusTransitionException(status, target);
        }
        this.status = target;
        if (target == UnitStatus.AVAILABLE) {
            this.activeIncidentId = null;
        }
        this.updatedAt = Instant.now();
    }

    public void dispatchTo(UUID incidentId) {
        changeStatus(UnitStatus.DISPATCHED);
        this.activeIncidentId = incidentId;
    }

    public void updateLocation(double latitude, double longitude) {
        this.currentLatitude = latitude;
        this.currentLongitude = longitude;
        this.updatedAt = Instant.now();
    }

    public boolean isAvailable() {
        return status == UnitStatus.AVAILABLE;
    }

    public UUID getId() { return id; }
    public String getCallSign() { return callSign; }
    public UnitType getType() { return type; }
    public UnitStatus getStatus() { return status; }
    public UUID getStationId() { return stationId; }
    public String getStationName() { return stationName; }
    public int getCrewSize() { return crewSize; }
    public double getBaseLatitude() { return baseLatitude; }
    public double getBaseLongitude() { return baseLongitude; }
    public Double getCurrentLatitude() { return currentLatitude; }
    public Double getCurrentLongitude() { return currentLongitude; }
    public UUID getActiveIncidentId() { return activeIncidentId; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public long getVersion() { return version; }
}
