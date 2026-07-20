package by.mchs.e112.gis.dto;

import by.mchs.e112.gis.domain.ServiceType;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record StationCreateRequest(
    @NotBlank @Size(max = 200) String name,
    @NotNull ServiceType serviceType,
    @NotBlank @Size(max = 500) String address,
    @DecimalMin("51.0") @DecimalMax("56.5") double latitude,
    @DecimalMin("23.0") @DecimalMax("33.0") double longitude,
    @Size(max = 20) String phone
) {
}
