package by.mchs.e112.gis.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import org.locationtech.jts.geom.Polygon;

/**
 * Зона ответственности станции (полигон выезда), PostGIS Polygon SRID 4326.
 */
@Entity
@Table(name = "response_zone")
public class ResponseZone {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Column(name = "station_id", nullable = false)
    private UUID stationId;

    @Enumerated(EnumType.STRING)
    @Column(name = "service_type", nullable = false, length = 20)
    private ServiceType serviceType;

    @Column(name = "area", nullable = false, columnDefinition = "geometry(Polygon,4326)")
    private Polygon area;

    protected ResponseZone() {
    }

    public ResponseZone(UUID id, String name, UUID stationId, ServiceType serviceType, Polygon area) {
        this.id = id;
        this.name = name;
        this.stationId = stationId;
        this.serviceType = serviceType;
        this.area = area;
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public UUID getStationId() { return stationId; }
    public ServiceType getServiceType() { return serviceType; }
    public Polygon getArea() { return area; }
}
