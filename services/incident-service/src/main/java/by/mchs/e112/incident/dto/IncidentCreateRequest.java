package by.mchs.e112.incident.dto;

import by.mchs.e112.incident.domain.IncidentPriority;
import by.mchs.e112.incident.domain.IncidentType;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.UUID;

public record IncidentCreateRequest(
    @NotNull IncidentType type,
    @NotNull IncidentPriority priority,
    @NotBlank @Size(max = 4000) String description,
    @NotBlank @Size(max = 500) String address,
    @DecimalMin(value = "51.0") @DecimalMax(value = "56.5") Double latitude,
    @DecimalMin(value = "23.0") @DecimalMax(value = "33.0") Double longitude,
    @Pattern(regexp = "^\\+?\\d{7,15}$", message = "Некорректный номер телефона") String callerPhone,
    @Size(max = 200) String callerName,
    @Min(0) int casualtiesCount,
    UUID callId
) {
}
