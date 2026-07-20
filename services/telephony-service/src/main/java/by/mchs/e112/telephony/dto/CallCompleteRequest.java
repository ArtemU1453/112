package by.mchs.e112.telephony.dto;

import jakarta.validation.constraints.Size;

public record CallCompleteRequest(
    @Size(max = 500) String recordingUrl
) {
}
