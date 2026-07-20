--liquibase formatted sql

--changeset e112:gis-001 runInTransaction:false
CREATE EXTENSION IF NOT EXISTS postgis;
CREATE EXTENSION IF NOT EXISTS pg_trgm;

--changeset e112:gis-002
CREATE TABLE station (
    id           UUID PRIMARY KEY,
    name         VARCHAR(200) NOT NULL,
    service_type VARCHAR(20)  NOT NULL,
    address      VARCHAR(500) NOT NULL,
    location     geometry(Point,4326) NOT NULL,
    phone        VARCHAR(20),
    CONSTRAINT chk_station_service CHECK (service_type IN ('FIRE','MEDICAL','POLICE','GAS','RESCUE'))
);
CREATE INDEX idx_station_location ON station USING gist (location);
CREATE INDEX idx_station_service ON station (service_type);

CREATE TABLE response_zone (
    id           UUID PRIMARY KEY,
    name         VARCHAR(200) NOT NULL,
    station_id   UUID NOT NULL,
    service_type VARCHAR(20)  NOT NULL,
    area         geometry(Polygon,4326) NOT NULL,
    CONSTRAINT fk_zone_station FOREIGN KEY (station_id) REFERENCES station (id) ON DELETE CASCADE,
    CONSTRAINT chk_zone_service CHECK (service_type IN ('FIRE','MEDICAL','POLICE','GAS','RESCUE'))
);
CREATE INDEX idx_zone_area ON response_zone USING gist (area);

CREATE TABLE geo_address (
    id        UUID PRIMARY KEY,
    region    VARCHAR(100) NOT NULL,
    city      VARCHAR(100) NOT NULL,
    street    VARCHAR(200) NOT NULL,
    house     VARCHAR(20)  NOT NULL,
    full_text VARCHAR(500) NOT NULL,
    location  geometry(Point,4326) NOT NULL
);
CREATE INDEX idx_address_location ON geo_address USING gist (location);
CREATE INDEX idx_address_fulltext_trgm ON geo_address USING gin (lower(full_text) gin_trgm_ops);
--rollback DROP TABLE geo_address; DROP TABLE response_zone; DROP TABLE station;
