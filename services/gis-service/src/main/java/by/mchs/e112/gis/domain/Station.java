package by.mchs.e112.gis.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import org.locationtech.jts.geom.Point;

/**
 * Пожарная часть / станция экстренной службы с геолокацией (PostGIS Point, SRID 4326).
 */
@Entity
@Table(name = "station")
public class Station {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "service_type", nullable = false, length = 20)
    private ServiceType serviceType;

    @Column(name = "address", nullable = false, length = 500)
    private String address;

    @Column(name = "location", nullable = false, columnDefinition = "geometry(Point,4326)")
    private Point location;

    @Column(name = "phone", length = 20)
    private String phone;

    protected Station() {
    }

    public Station(UUID id, String name, ServiceType serviceType, String address, Point location, String phone) {
        this.id = id;
        this.name = name;
        this.serviceType = serviceType;
        this.address = address;
        this.location = location;
        this.phone = phone;
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public ServiceType getServiceType() { return serviceType; }
    public String getAddress() { return address; }
    public Point getLocation() { return location; }
    public String getPhone() { return phone; }
}
