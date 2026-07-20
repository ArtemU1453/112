package by.mchs.e112.auth.dto;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public record UserResponse(
    UUID id,
    String keycloakId,
    String username,
    String firstName,
    String lastName,
    String email,
    String phone,
    String employeeNumber,
    String department,
    String status,
    Set<String> roles,
    Instant createdAt,
    Instant updatedAt
) {
}
