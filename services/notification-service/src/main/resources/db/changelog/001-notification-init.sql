--liquibase formatted sql

--changeset e112:notification-001
CREATE TABLE notification (
    id                UUID PRIMARY KEY,
    channel           VARCHAR(10)  NOT NULL,
    recipient         VARCHAR(255) NOT NULL,
    subject           VARCHAR(255),
    body              VARCHAR(2000) NOT NULL,
    status            VARCHAR(10)  NOT NULL DEFAULT 'PENDING',
    related_entity_id VARCHAR(64),
    error_message     VARCHAR(1000),
    created_at        TIMESTAMPTZ  NOT NULL DEFAULT now(),
    sent_at           TIMESTAMPTZ,
    CONSTRAINT chk_notification_channel CHECK (channel IN ('SMS','EMAIL','PUSH')),
    CONSTRAINT chk_notification_status CHECK (status IN ('PENDING','SENT','FAILED'))
);
CREATE INDEX idx_notification_status ON notification (status);
CREATE INDEX idx_notification_related ON notification (related_entity_id);
CREATE INDEX idx_notification_created_at ON notification (created_at DESC);
--rollback DROP TABLE notification;
