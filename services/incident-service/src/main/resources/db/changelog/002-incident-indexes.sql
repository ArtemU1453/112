--liquibase formatted sql

--changeset e112:incident-002
CREATE INDEX idx_incident_status ON incident (status);
CREATE INDEX idx_incident_type ON incident (type);
CREATE INDEX idx_incident_priority ON incident (priority);
CREATE INDEX idx_incident_created_at ON incident (created_at DESC);
CREATE INDEX idx_incident_call_id ON incident (call_id);
CREATE INDEX idx_incident_address_trgm ON incident USING gin (lower(address) gin_trgm_ops);
CREATE INDEX idx_incident_history_incident ON incident_history (incident_id, occurred_at);
--rollback DROP INDEX idx_incident_status, idx_incident_type, idx_incident_priority, idx_incident_created_at, idx_incident_call_id, idx_incident_address_trgm, idx_incident_history_incident;
