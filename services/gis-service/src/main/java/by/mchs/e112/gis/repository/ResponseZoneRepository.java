package by.mchs.e112.gis.repository;

import by.mchs.e112.gis.domain.ResponseZone;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ResponseZoneRepository extends JpaRepository<ResponseZone, UUID> {

    /**
     * Зоны ответственности, содержащие заданную точку (PostGIS ST_Contains).
     */
    @Query(value = """
        SELECT z.id, z.name, z.station_id, z.service_type
        FROM response_zone z
        WHERE ST_Contains(z.area, ST_SetSRID(ST_MakePoint(:lon, :lat), 4326))
        """, nativeQuery = true)
    List<Object[]> findZonesContaining(@Param("lat") double lat, @Param("lon") double lon);
}
