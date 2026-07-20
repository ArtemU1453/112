package by.mchs.e112.gis.web;

import by.mchs.e112.gis.domain.ServiceType;
import by.mchs.e112.gis.dto.GeocodeRequest;
import by.mchs.e112.gis.dto.GeocodeResponse;
import by.mchs.e112.gis.dto.NearestStationResponse;
import by.mchs.e112.gis.dto.ResponseZoneResponse;
import by.mchs.e112.gis.dto.ReverseGeocodeResponse;
import by.mchs.e112.gis.dto.StationCreateRequest;
import by.mchs.e112.gis.dto.StationResponse;
import by.mchs.e112.gis.service.GeoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/gis")
@Tag(name = "ГИС", description = "Геокодирование, станции, зоны ответственности")
public class GisController {

    private final GeoService geoService;

    public GisController(GeoService geoService) {
        this.geoService = geoService;
    }

    @PostMapping("/geocode")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Прямое геокодирование: адрес → координаты")
    public GeocodeResponse geocode(@Valid @RequestBody GeocodeRequest request) {
        return geoService.geocode(request.address());
    }

    @GetMapping("/reverse-geocode")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Обратное геокодирование: координаты → адрес")
    public ReverseGeocodeResponse reverseGeocode(@RequestParam double lat, @RequestParam double lon) {
        return geoService.reverseGeocode(lat, lon);
    }

    @GetMapping("/stations/nearest")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Ближайшие станции заданной службы")
    public List<NearestStationResponse> nearestStations(@RequestParam double lat,
                                                        @RequestParam double lon,
                                                        @RequestParam ServiceType serviceType,
                                                        @RequestParam(defaultValue = "3") int limit) {
        return geoService.nearestStations(lat, lon, serviceType, limit);
    }

    @GetMapping("/zones")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Зоны ответственности, содержащие точку")
    public List<ResponseZoneResponse> zones(@RequestParam double lat, @RequestParam double lon) {
        return geoService.zonesContaining(lat, lon);
    }

    @PostMapping("/stations")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Добавить станцию")
    public StationResponse createStation(@Valid @RequestBody StationCreateRequest request) {
        return geoService.createStation(request);
    }

    @GetMapping("/stations")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Список всех станций")
    public List<StationResponse> getAllStations() {
        return geoService.getAllStations();
    }
}
