package by.mchs.e112.auth.dto;

import jakarta.validation.constraints.NotEmpty;
import java.util.Set;

public record RoleAssignRequest(@NotEmpty Set<String> roles) {
}
