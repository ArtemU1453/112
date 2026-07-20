--liquibase formatted sql

--changeset e112:dispatch-001
CREATE TABLE unit (
    id                UUID PRIMARY KEY,
    call_sign         VARCHAR(30)  NOT NULL,
    type              VARCHAR(20)  NOT NULL,
    status            VARCHAR(20)  NOT NULL DEFAULT 'AVAILABLE',
    station_id        UUID         NOT NULL,
    station_name      VARCHAR(200) NOT NULL,
    crew_size         INTEGER      NOT NULL,
    base_latitude     DOUBLE PRECISION NOT NULL,
    base_longitude    DOUBLE PRECISION NOT NULL,
    current_latitude  DOUBLE PRECISION,
    current_longitude DOUBLE PRECISION,
    active_incident_id UUID,
    created_at        TIMESTAMPTZ  NOT NULL DEFAULT now(),
    updated_at        TIMESTAMPTZ  NOT NULL DEFAULT now(),
    version           BIGINT       NOT NULL DEFAULT 0,
    CONSTRAINT uq_unit_call_sign UNIQUE (call_sign),
    CONSTRAINT chk_unit_type CHECK (type IN ('FIRE_TRUCK','LADDER_TRUCK','AMBULANCE','RESCUE_SQUAD',
        'POLICE_PATROL','GAS_SERVICE','HAZMAT_UNIT','WATER_RESCUE','COMMAND_VEHICLE')),
    CONSTRAINT chk_unit_status CHECK (status IN ('AVAILABLE','DISPATCHED','EN_ROUTE','ON_SCENE',
        'RETURNING','OUT_OF_SERVICE')),
    CONSTRAINT chk_unit_crew CHECK (crew_size >= 1)
);
CREATE INDEX idx_unit_status ON unit (status);
CREATE INDEX idx_unit_type_status ON unit (type, status);
CREATE INDEX idx_unit_station ON unit (station_id);

CREATE TABLE assignment (
    id             UUID PRIMARY KEY,
    incident_id    UUID        NOT NULL,
    unit_id        UUID        NOT NULL,
    unit_call_sign VARCHAR(30) NOT NULL,
    status         VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    assigned_by    VARCHAR(64) NOT NULL,
    distance_km    DOUBLE PRECISION,
    assigned_at    TIMESTAMPTZ NOT NULL DEFAULT now(),
    completed_at   TIMESTAMPTZ,
    CONSTRAINT fk_assignment_unit FOREIGN KEY (unit_id) REFERENCES unit (id),
    CONSTRAINT chk_assignment_status CHECK (status IN ('ACTIVE','COMPLETED','RECALLED'))
);
CREATE INDEX idx_assignment_incident ON assignment (incident_id);
CREATE INDEX idx_assignment_unit ON assignment (unit_id);
CREATE INDEX idx_assignment_status ON assignment (status);
--rollback DROP TABLE assignment; DROP TABLE unit;
