package by.mchs.e112.gis.service;

import by.mchs.e112.gis.domain.ServiceType;
import by.mchs.e112.gis.domain.Station;
import by.mchs.e112.gis.dto.GeocodeResponse;
import by.mchs.e112.gis.dto.NearestStationResponse;
import by.mchs.e112.gis.dto.ResponseZoneResponse;
import by.mchs.e112.gis.dto.ReverseGeocodeResponse;
import by.mchs.e112.gis.dto.StationCreateRequest;
import by.mchs.e112.gis.dto.StationResponse;
import by.mchs.e112.gis.exception.AddressNotFoundException;
import by.mchs.e112.gis.repository.GeoAddressRepository;
import by.mchs.e112.gis.repository.ResponseZoneRepository;
import by.mchs.e112.gis.repository.StationRepository;
import java.util.List;
import java.util.UUID;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GeoService {

    private static final int SRID_WGS84 = 4326;

    private final StationRepository stationRepository;
    private final ResponseZoneRepository zoneRepository;
    private final GeoAddressRepository addressRepository;
    private final GeometryFactory geometryFactory =
        new GeometryFactory(new PrecisionModel(), SRID_WGS84);

    public GeoService(StationRepository stationRepository,
                      ResponseZoneRepository zoneRepository,
                      GeoAddressRepository addressRepository) {
        this.stationRepository = stationRepository;
        this.zoneRepository = zoneRepository;
        this.addressRepository = addressRepository;
    }

    @Transactional(readOnly = true)
    public GeocodeResponse geocode(String address) {
        List<Object[]> rows = addressRepository.searchByText(address, 1);
        if (rows.isEmpty()) {
            throw new AddressNotFoundException(address);
        }
        Object[] row = rows.get(0);
        return new GeocodeResponse(
            (String) row[0],
            ((Number) row[1]).doubleValue(),
            ((Number) row[2]).doubleValue(),
            ((Number) row[3]).doubleValue());
    }

    @Transactional(readOnly = true)
    public ReverseGeocodeResponse reverseGeocode(double lat, double lon) {
        List<Object[]> rows = addressRepository.findNearestAddress(lat, lon);
        if (rows.isEmpty()) {
            throw new AddressNotFoundException("%.5f,%.5f".formatted(lat, lon));
        }
        Object[] row = rows.get(0);
        return new ReverseGeocodeResponse(
            (String) row[0],
            ((Number) row[1]).doubleValue(),
            ((Number) row[2]).doubleValue(),
            ((Number) row[3]).doubleValue());
    }

    @Transactional(readOnly = true)
    public List<NearestStationResponse> nearestStations(double lat, double lon, ServiceType type, int limit) {
        return stationRepository.findNearest(lat, lon, type.name(), limit).stream()
            .map(row -> new NearestStationResponse(
                (UUID) row[0],
                (String) row[1],
                (String) row[2],
                (String) row[3],
                ((Number) row[4]).doubleValue(),
                ((Number) row[5]).doubleValue(),
                ((Number) row[6]).doubleValue()))
            .toList();
    }

    @Transactional(readOnly = true)
    public List<ResponseZoneResponse> zonesContaining(double lat, double lon) {
        return zoneRepository.findZonesContaining(lat, lon).stream()
            .map(row -> new ResponseZoneResponse(
                (UUID) row[0],
                (String) row[1],
                (UUID) row[2],
                (String) row[3]))
            .toList();
    }

    @Transactional
    public StationResponse createStation(StationCreateRequest request) {
        Point point = geometryFactory.createPoint(new Coordinate(request.longitude(), request.latitude()));
        point.setSRID(SRID_WGS84);
        Station station = new Station(UUID.randomUUID(), request.name(), request.serviceType(),
            request.address(), point, request.phone());
        stationRepository.save(station);
        return toResponse(station);
    }

    @Transactional(readOnly = true)
    public List<StationResponse> getAllStations() {
        return stationRepository.findAll().stream().map(this::toResponse).toList();
    }

    private StationResponse toResponse(Station station) {
        return new StationResponse(
            station.getId(),
            station.getName(),
            station.getServiceType().name(),
            station.getAddress(),
            station.getLocation().getY(),
            station.getLocation().getX(),
            station.getPhone());
    }
}
