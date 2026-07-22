--liquibase formatted sql

--changeset e112:incident-001
CREATE SEQUENCE incident_number_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE incident (
    id               UUID PRIMARY KEY,
    number           VARCHAR(20),
    seq_no           BIGINT       NOT NULL DEFAULT nextval('incident_number_seq'),
    type             VARCHAR(30)  NOT NULL,
    priority         VARCHAR(10)  NOT NULL,
    status           VARCHAR(20)  NOT NULL DEFAULT 'RECEIVED',
    description      VARCHAR(4000) NOT NULL,
    address          VARCHAR(500) NOT NULL,
    latitude         DOUBLE PRECISION,
    longitude        DOUBLE PRECISION,
    caller_phone     VARCHAR(20),
    caller_name      VARCHAR(200),
    casualties_count INTEGER      NOT NULL DEFAULT 0,
    call_id          UUID,
    created_by       VARCHAR(64)  NOT NULL,
    closed_reason    VARCHAR(1000),
    created_at       TIMESTAMPTZ  NOT NULL DEFAULT now(),
    updated_at       TIMESTAMPTZ  NOT NULL DEFAULT now(),
    version          BIGINT       NOT NULL DEFAULT 0,
    CONSTRAINT uq_incident_number UNIQUE (number),
    CONSTRAINT chk_incident_type CHECK (type IN ('FIRE','MEDICAL','POLICE','GAS_LEAK','TRAFFIC_ACCIDENT',
        'WATER_RESCUE','HAZMAT','TECHNOLOGICAL','NATURAL','FALSE_ALARM','OTHER')),
    CONSTRAINT chk_incident_priority CHECK (priority IN ('CRITICAL','HIGH','MEDIUM','LOW')),
    CONSTRAINT chk_incident_status CHECK (status IN ('RECEIVED','CLASSIFIED','DISPATCHED',
        'IN_PROGRESS','RESOLVED','CLOSED','CANCELLED')),
    CONSTRAINT chk_incident_casualties CHECK (casualties_count >= 0)
);

CREATE TABLE incident_history (
    id          BIGSERIAL PRIMARY KEY,
    incident_id UUID        NOT NULL,
    action      VARCHAR(50) NOT NULL,
    actor       VARCHAR(64) NOT NULL,
    comment     VARCHAR(2000),
    occurred_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT fk_history_incident FOREIGN KEY (incident_id) REFERENCES incident (id) ON DELETE CASCADE
);
--rollback DROP TABLE incident_history; DROP TABLE incident; DROP SEQUENCE incident_number_seq;

--changeset e112:incident-001-pg-trgm
--comment: Расширение pg_trgm требуется для триграммного GIN-индекса в 002 (в проде создаётся init-скриптом; в changelog — для самодостаточности схемы, напр. Testcontainers). Идемпотентно.
CREATE EXTENSION IF NOT EXISTS pg_trgm;
--rollback DROP EXTENSION IF EXISTS pg_trgm;
