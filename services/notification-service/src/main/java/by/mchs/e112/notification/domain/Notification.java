package by.mchs.e112.notification.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;

/**
 * Уведомление получателю по одному из каналов с журналом доставки.
 */
@Entity
@Table(name = "notification")
public class Notification {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "channel", nullable = false, length = 10)
    private NotificationChannel channel;

    @Column(name = "recipient", nullable = false, length = 255)
    private String recipient;

    @Column(name = "subject", length = 255)
    private String subject;

    @Column(name = "body", nullable = false, length = 2000)
    private String body;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 10)
    private NotificationStatus status;

    @Column(name = "related_entity_id", length = 64)
    private String relatedEntityId;

    @Column(name = "error_message", length = 1000)
    private String errorMessage;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "sent_at")
    private Instant sentAt;

    protected Notification() {
    }

    public Notification(UUID id, NotificationChannel channel, String recipient, String subject,
                        String body, String relatedEntityId) {
        this.id = id;
        this.channel = channel;
        this.recipient = recipient;
        this.subject = subject;
        this.body = body;
        this.status = NotificationStatus.PENDING;
        this.relatedEntityId = relatedEntityId;
        this.createdAt = Instant.now();
    }

    public void markSent() {
        this.status = NotificationStatus.SENT;
        this.sentAt = Instant.now();
    }

    public void markFailed(String error) {
        this.status = NotificationStatus.FAILED;
        this.errorMessage = error;
    }

    public UUID getId() { return id; }
    public NotificationChannel getChannel() { return channel; }
    public String getRecipient() { return recipient; }
    public String getSubject() { return subject; }
    public String getBody() { return body; }
    public NotificationStatus getStatus() { return status; }
    public String getRelatedEntityId() { return relatedEntityId; }
    public String getErrorMessage() { return errorMessage; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getSentAt() { return sentAt; }
}
