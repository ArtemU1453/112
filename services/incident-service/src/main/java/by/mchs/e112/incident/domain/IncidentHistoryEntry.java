package by.mchs.e112.incident.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;

/**
 * Запись истории изменений карточки (event sourcing-lite для аудита).
 */
@Entity
@Table(name = "incident_history")
public class IncidentHistoryEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "incident_id", nullable = false)
    private Incident incident;

    @Column(name = "action", nullable = false, length = 50)
    private String action;

    @Column(name = "actor", nullable = false, length = 64)
    private String actor;

    @Column(name = "comment", length = 2000)
    private String comment;

    @Column(name = "occurred_at", nullable = false)
    private Instant occurredAt;

    protected IncidentHistoryEntry() {
    }

    public IncidentHistoryEntry(Incident incident, String action, String actor, String comment) {
        this.incident = incident;
        this.action = action;
        this.actor = actor;
        this.comment = comment;
        this.occurredAt = Instant.now();
    }

    public Long getId() { return id; }
    public Incident getIncident() { return incident; }
    public String getAction() { return action; }
    public String getActor() { return actor; }
    public String getComment() { return comment; }
    public Instant getOccurredAt() { return occurredAt; }
}
