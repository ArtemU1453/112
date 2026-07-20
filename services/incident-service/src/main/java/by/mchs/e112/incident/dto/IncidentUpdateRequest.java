package by.mchs.e112.incident.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record IncidentUpdateRequest(
    @NotBlank @Size(max = 4000) String description,
    @NotBlank @Size(max = 500) String address,
    @DecimalMin(value = "51.0") @DecimalMax(value = "56.5") Double latitude,
    @DecimalMin(value = "23.0") @DecimalMax(value = "33.0") Double longitude,
    @Size(max = 200) String callerName,
    @Min(0) int casualtiesCount
) {
}
