--liquibase formatted sql

--changeset e112:telephony-001
CREATE TABLE call (
    id               UUID PRIMARY KEY,
    caller_phone     VARCHAR(20) NOT NULL,
    direction        VARCHAR(10) NOT NULL,
    status           VARCHAR(15) NOT NULL DEFAULT 'RINGING',
    operator         VARCHAR(64),
    recording_url    VARCHAR(500),
    transcript       VARCHAR(8000),
    incident_id      UUID,
    started_at       TIMESTAMPTZ NOT NULL DEFAULT now(),
    answered_at      TIMESTAMPTZ,
    ended_at         TIMESTAMPTZ,
    duration_seconds BIGINT,
    CONSTRAINT chk_call_direction CHECK (direction IN ('INBOUND','OUTBOUND')),
    CONSTRAINT chk_call_status CHECK (status IN ('RINGING','ACTIVE','ON_HOLD','COMPLETED',
        'MISSED','TRANSCRIBED','ANALYZED'))
);
CREATE INDEX idx_call_status ON call (status);
CREATE INDEX idx_call_caller_phone ON call (caller_phone);
CREATE INDEX idx_call_operator ON call (operator);
CREATE INDEX idx_call_incident ON call (incident_id);
CREATE INDEX idx_call_started_at ON call (started_at DESC);
--rollback DROP TABLE call;
