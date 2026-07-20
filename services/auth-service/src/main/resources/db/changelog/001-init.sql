--liquibase formatted sql

--changeset e112:auth-001
CREATE TABLE user_profile (
    id              UUID PRIMARY KEY,
    keycloak_id     VARCHAR(64)  NOT NULL,
    username        VARCHAR(64)  NOT NULL,
    first_name      VARCHAR(100) NOT NULL,
    last_name       VARCHAR(100) NOT NULL,
    email           VARCHAR(255) NOT NULL,
    phone           VARCHAR(20),
    employee_number VARCHAR(20),
    department      VARCHAR(200),
    status          VARCHAR(20)  NOT NULL DEFAULT 'ACTIVE',
    created_at      TIMESTAMPTZ  NOT NULL DEFAULT now(),
    updated_at      TIMESTAMPTZ  NOT NULL DEFAULT now(),
    CONSTRAINT uq_user_profile_keycloak_id UNIQUE (keycloak_id),
    CONSTRAINT uq_user_profile_username UNIQUE (username),
    CONSTRAINT chk_user_profile_status CHECK (status IN ('ACTIVE', 'BLOCKED'))
);
CREATE INDEX idx_user_profile_department ON user_profile (department);
CREATE INDEX idx_user_profile_status ON user_profile (status);
--rollback DROP TABLE user_profile;
