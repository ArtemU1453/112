package by.mchs.e112.telephony.dto;

import by.mchs.e112.telephony.domain.CallDirection;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CallStartRequest(
    @Pattern(regexp = "^\\+?\\d{3,15}$", message = "Некорректный номер телефона") String callerPhone,
    @NotNull CallDirection direction
) {
}
