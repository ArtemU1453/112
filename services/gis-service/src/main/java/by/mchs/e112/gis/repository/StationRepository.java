package by.mchs.e112.gis.repository;

import by.mchs.e112.gis.domain.ServiceType;
import by.mchs.e112.gis.domain.Station;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StationRepository extends JpaRepository<Station, UUID> {

    /**
     * Ближайшие станции заданной службы к точке, отсортированные по расстоянию (PostGIS ST_Distance по географии).
     */
    @Query(value = """
        SELECT s.id, s.name, s.service_type, s.address,
               ST_Y(s.location) AS lat, ST_X(s.location) AS lon,
               ST_Distance(s.location::geography, ST_SetSRID(ST_MakePoint(:lon, :lat), 4326)::geography) / 1000.0 AS distance_km
        FROM station s
        WHERE s.service_type = :serviceType
        ORDER BY s.location <-> ST_SetSRID(ST_MakePoint(:lon, :lat), 4326)
        LIMIT :limit
        """, nativeQuery = true)
    List<Object[]> findNearest(@Param("lat") double lat, @Param("lon") double lon,
                               @Param("serviceType") String serviceType, @Param("limit") int limit);
}
