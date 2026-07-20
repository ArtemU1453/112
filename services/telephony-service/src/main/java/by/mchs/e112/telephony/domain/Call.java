package by.mchs.e112.telephony.domain;

import by.mchs.e112.telephony.exception.IllegalCallStatusTransitionException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

/**
 * Экстренный вызов: телефонная сессия гражданина с оператором 112.
 */
@Entity
@Table(name = "call")
public class Call {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "caller_phone", nullable = false, length = 20)
    private String callerPhone;

    @Enumerated(EnumType.STRING)
    @Column(name = "direction", nullable = false, length = 10)
    private CallDirection direction;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 15)
    private CallStatus status;

    @Column(name = "operator", length = 64)
    private String operator;

    @Column(name = "recording_url", length = 500)
    private String recordingUrl;

    @Column(name = "transcript", length = 8000)
    private String transcript;

    @Column(name = "incident_id")
    private UUID incidentId;

    @Column(name = "started_at", nullable = false)
    private Instant startedAt;

    @Column(name = "answered_at")
    private Instant answeredAt;

    @Column(name = "ended_at")
    private Instant endedAt;

    @Column(name = "duration_seconds")
    private Long durationSeconds;

    protected Call() {
    }

    public Call(UUID id, String callerPhone, CallDirection direction) {
        this.id = id;
        this.callerPhone = callerPhone;
        this.direction = direction;
        this.status = CallStatus.RINGING;
        this.startedAt = Instant.now();
    }

    public void answer(String operator) {
        transition(CallStatus.ACTIVE);
        this.operator = operator;
        this.answeredAt = Instant.now();
    }

    public void hold() {
        transition(CallStatus.ON_HOLD);
    }

    public void resume() {
        transition(CallStatus.ACTIVE);
    }

    public void complete(String recordingUrl) {
        transition(CallStatus.COMPLETED);
        this.recordingUrl = recordingUrl;
        this.endedAt = Instant.now();
        this.durationSeconds = Duration.between(startedAt, endedAt).getSeconds();
    }

    public void miss() {
        transition(CallStatus.MISSED);
        this.endedAt = Instant.now();
        this.durationSeconds = 0L;
    }

    public void attachTranscript(String transcript) {
        transition(CallStatus.TRANSCRIBED);
        this.transcript = transcript;
    }

    public void markAnalyzed(UUID incidentId) {
        transition(CallStatus.ANALYZED);
        this.incidentId = incidentId;
    }

    public void linkIncident(UUID incidentId) {
        this.incidentId = incidentId;
    }

    private void transition(CallStatus target) {
        if (!status.canTransitionTo(target)) {
            throw new IllegalCallStatusTransitionException(status, target);
        }
        this.status = target;
    }

    public UUID getId() { return id; }
    public String getCallerPhone() { return callerPhone; }
    public CallDirection getDirection() { return direction; }
    public CallStatus getStatus() { return status; }
    public String getOperator() { return operator; }
    public String getRecordingUrl() { return recordingUrl; }
    public String getTranscript() { return transcript; }
    public UUID getIncidentId() { return incidentId; }
    public Instant getStartedAt() { return startedAt; }
    public Instant getAnsweredAt() { return answeredAt; }
    public Instant getEndedAt() { return endedAt; }
    public Long getDurationSeconds() { return durationSeconds; }
}
