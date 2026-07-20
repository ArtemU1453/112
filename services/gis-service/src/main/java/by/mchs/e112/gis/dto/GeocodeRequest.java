package by.mchs.e112.gis.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record GeocodeRequest(
    @NotBlank @Size(max = 500) String address
) {
}
