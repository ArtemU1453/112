--liquibase formatted sql

--changeset e112:analytics-001
CREATE TABLE analytics_incident (
    incident_id      UUID PRIMARY KEY,
    number           VARCHAR(32),
    type             VARCHAR(40)  NOT NULL,
    priority         VARCHAR(20)  NOT NULL,
    status           VARCHAR(20)  NOT NULL,
    casualties_count INT          NOT NULL DEFAULT 0,
    latitude         DOUBLE PRECISION,
    longitude        DOUBLE PRECISION,
    created_at       TIMESTAMPTZ  NOT NULL,
    updated_at       TIMESTAMPTZ,
    resolved_at      TIMESTAMPTZ,
    closed_at        TIMESTAMPTZ
);
CREATE INDEX idx_ai_created_at ON analytics_incident (created_at DESC);
CREATE INDEX idx_ai_type ON analytics_incident (type);
CREATE INDEX idx_ai_priority ON analytics_incident (priority);
CREATE INDEX idx_ai_status ON analytics_incident (status);

CREATE TABLE analytics_dispatch (
    assignment_id  UUID PRIMARY KEY,
    incident_id    UUID         NOT NULL,
    unit_id        UUID         NOT NULL,
    unit_call_sign VARCHAR(40),
    distance_km    DOUBLE PRECISION,
    dispatched_at  TIMESTAMPTZ  NOT NULL
);
CREATE INDEX idx_ad_incident ON analytics_dispatch (incident_id);
CREATE INDEX idx_ad_dispatched_at ON analytics_dispatch (dispatched_at);
--rollback DROP TABLE analytics_dispatch;
--rollback DROP TABLE analytics_incident;
