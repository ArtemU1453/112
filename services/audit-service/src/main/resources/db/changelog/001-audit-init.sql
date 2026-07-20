--liquibase formatted sql

--changeset e112:audit-001
CREATE TABLE audit_event (
    id          UUID PRIMARY KEY,
    service     VARCHAR(50)  NOT NULL,
    action      VARCHAR(60)  NOT NULL,
    entity_type VARCHAR(30)  NOT NULL,
    entity_id   VARCHAR(64)  NOT NULL,
    actor       VARCHAR(64)  NOT NULL,
    details     JSONB,
    occurred_at TIMESTAMPTZ  NOT NULL,
    recorded_at TIMESTAMPTZ  NOT NULL DEFAULT now()
);
CREATE INDEX idx_audit_service ON audit_event (service);
CREATE INDEX idx_audit_action ON audit_event (action);
CREATE INDEX idx_audit_entity ON audit_event (entity_type, entity_id);
CREATE INDEX idx_audit_actor ON audit_event (actor);
CREATE INDEX idx_audit_occurred_at ON audit_event (occurred_at DESC);
CREATE INDEX idx_audit_details_gin ON audit_event USING gin (details);
--rollback DROP TABLE audit_event;
