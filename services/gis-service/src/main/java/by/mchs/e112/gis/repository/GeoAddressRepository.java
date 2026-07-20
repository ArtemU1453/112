package by.mchs.e112.gis.repository;

import by.mchs.e112.gis.domain.GeoAddress;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GeoAddressRepository extends JpaRepository<GeoAddress, UUID> {

    /**
     * Нечёткий поиск адреса по полнотекстовому представлению (pg_trgm similarity).
     */
    @Query(value = """
        SELECT a.full_text, ST_Y(a.location) AS lat, ST_X(a.location) AS lon,
               similarity(lower(a.full_text), lower(:query)) AS score
        FROM geo_address a
        WHERE lower(a.full_text) % lower(:query)
        ORDER BY score DESC
        LIMIT :limit
        """, nativeQuery = true)
    List<Object[]> searchByText(@Param("query") String query, @Param("limit") int limit);

    /**
     * Обратное геокодирование: ближайший адрес к точке.
     */
    @Query(value = """
        SELECT a.full_text, ST_Y(a.location) AS lat, ST_X(a.location) AS lon,
               ST_Distance(a.location::geography, ST_SetSRID(ST_MakePoint(:lon, :lat), 4326)::geography) AS distance_m
        FROM geo_address a
        ORDER BY a.location <-> ST_SetSRID(ST_MakePoint(:lon, :lat), 4326)
        LIMIT 1
        """, nativeQuery = true)
    List<Object[]> findNearestAddress(@Param("lat") double lat, @Param("lon") double lon);
}
