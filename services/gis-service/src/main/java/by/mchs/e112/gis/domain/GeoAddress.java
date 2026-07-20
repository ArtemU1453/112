package by.mchs.e112.gis.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import org.locationtech.jts.geom.Point;

/**
 * Справочник адресов (геокодер): нормализованный адрес ↔ координаты.
 */
@Entity
@Table(name = "geo_address")
public class GeoAddress {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "region", nullable = false, length = 100)
    private String region;

    @Column(name = "city", nullable = false, length = 100)
    private String city;

    @Column(name = "street", nullable = false, length = 200)
    private String street;

    @Column(name = "house", nullable = false, length = 20)
    private String house;

    @Column(name = "full_text", nullable = false, length = 500)
    private String fullText;

    @Column(name = "location", nullable = false, columnDefinition = "geometry(Point,4326)")
    private Point location;

    protected GeoAddress() {
    }

    public GeoAddress(UUID id, String region, String city, String street, String house,
                      String fullText, Point location) {
        this.id = id;
        this.region = region;
        this.city = city;
        this.street = street;
        this.house = house;
        this.fullText = fullText;
        this.location = location;
    }

    public UUID getId() { return id; }
    public String getRegion() { return region; }
    public String getCity() { return city; }
    public String getStreet() { return street; }
    public String getHouse() { return house; }
    public String getFullText() { return fullText; }
    public Point getLocation() { return location; }
}
