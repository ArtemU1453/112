package by.mchs.e112.dispatch.dto;

import by.mchs.e112.dispatch.domain.UnitStatus;
import jakarta.validation.constraints.NotNull;

public record UnitStatusChangeRequest(@NotNull UnitStatus status) {
}
