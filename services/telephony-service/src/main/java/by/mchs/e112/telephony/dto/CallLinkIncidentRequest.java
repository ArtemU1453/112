package by.mchs.e112.telephony.dto;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record CallLinkIncidentRequest(@NotNull UUID incidentId) {
}
