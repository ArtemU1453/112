package by.mchs.e112.incident.domain;

import by.mchs.e112.incident.exception.IllegalStatusTransitionException;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Агрегат «Карточка происшествия» — корень домена системы 112.
 */
@Entity
@Table(name = "incident")
public class Incident {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    /** Человекочитаемый номер: 112-2026-000123. */
    @Column(name = "number", nullable = false, unique = true, length = 20)
    private String number;

    @SequenceGenerator(name = "incident_seq", sequenceName = "incident_number_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "incident_seq")
    @Column(name = "seq_no", nullable = false)
    private Long seqNo;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 30)
    private IncidentType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false, length = 10)
    private IncidentPriority priority;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private IncidentStatus status;

    @Column(name = "description", nullable = false, length = 4000)
    private String description;

    @Column(name = "address", nullable = false, length = 500)
    private String address;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "caller_phone", length = 20)
    private String callerPhone;

    @Column(name = "caller_name", length = 200)
    private String callerName;

    @Column(name = "casualties_count", nullable = false)
    private int casualtiesCount;

    @Column(name = "call_id")
    private UUID callId;

    @Column(name = "created_by", nullable = false, length = 64)
    private String createdBy;

    @Column(name = "closed_reason", length = 1000)
    private String closedReason;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Version
    @Column(name = "version", nullable = false)
    private long version;

    @OneToMany(mappedBy = "incident", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderBy("occurredAt ASC")
    private List<IncidentHistoryEntry> history = new ArrayList<>();

    protected Incident() {
    }

    public Incident(UUID id, IncidentType type, IncidentPriority priority, String description,
                    String address, Double latitude, Double longitude, String callerPhone,
                    String callerName, int casualtiesCount, UUID callId, String createdBy) {
        this.id = id;
        this.type = type;
        this.priority = priority;
        this.status = IncidentStatus.RECEIVED;
        this.description = description;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.callerPhone = callerPhone;
        this.callerName = callerName;
        this.casualtiesCount = casualtiesCount;
        this.callId = callId;
        this.createdBy = createdBy;
        this.createdAt = Instant.now();
        this.updatedAt = this.createdAt;
        addHistory("CREATED", createdBy, "Карточка создана");
    }

    /** Номер формируется после первой записи, когда БД выдала seq_no. */
    public void assignNumber(int year) {
        this.number = "112-%d-%06d".formatted(year, seqNo);
    }

    public void changeStatus(IncidentStatus target, String actor, String comment) {
        if (!status.canTransitionTo(target)) {
            throw new IllegalStatusTransitionException(status, target);
        }
        this.status = target;
        this.updatedAt = Instant.now();
        addHistory("STATUS_" + target.name(), actor, comment);
    }

    public void classify(IncidentType type, IncidentPriority priority, String actor) {
        this.type = type;
        this.priority = priority;
        if (status == IncidentStatus.RECEIVED) {
            changeStatus(IncidentStatus.CLASSIFIED, actor, "Классифицировано: %s/%s".formatted(type, priority));
        } else {
            this.updatedAt = Instant.now();
            addHistory("RECLASSIFIED", actor, "Переклассифицировано: %s/%s".formatted(type, priority));
        }
    }

    public void updateDetails(String description, String address, Double latitude, Double longitude,
                              String callerName, int casualtiesCount, String actor) {
        this.description = description;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.callerName = callerName;
        this.casualtiesCount = casualtiesCount;
        this.updatedAt = Instant.now();
        addHistory("DETAILS_UPDATED", actor, "Данные карточки обновлены");
    }

    public void close(String actor, String reason) {
        changeStatus(IncidentStatus.CLOSED, actor, reason);
        this.closedReason = reason;
    }

    public void cancel(String actor, String reason) {
        changeStatus(IncidentStatus.CANCELLED, actor, reason);
        this.closedReason = reason;
    }

    public void markDispatched(String actor, String unitInfo) {
        if (status == IncidentStatus.RECEIVED || status == IncidentStatus.CLASSIFIED) {
            changeStatus(IncidentStatus.DISPATCHED, actor, "Назначен наряд: " + unitInfo);
        } else {
            addHistory("UNIT_ASSIGNED", actor, "Дополнительный наряд: " + unitInfo);
        }
    }

    private void addHistory(String action, String actor, String comment) {
        history.add(new IncidentHistoryEntry(this, action, actor, comment));
    }

    public UUID getId() { return id; }
    public String getNumber() { return number; }
    public Long getSeqNo() { return seqNo; }
    public IncidentType getType() { return type; }
    public IncidentPriority getPriority() { return priority; }
    public IncidentStatus getStatus() { return status; }
    public String getDescription() { return description; }
    public String getAddress() { return address; }
    public Double getLatitude() { return latitude; }
    public Double getLongitude() { return longitude; }
    public String getCallerPhone() { return callerPhone; }
    public String getCallerName() { return callerName; }
    public int getCasualtiesCount() { return casualtiesCount; }
    public UUID getCallId() { return callId; }
    public String getCreatedBy() { return createdBy; }
    public String getClosedReason() { return closedReason; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public long getVersion() { return version; }
    public List<IncidentHistoryEntry> getHistory() { return List.copyOf(history); }
}
