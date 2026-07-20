package com.easur.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateRequest(@NotBlank String username, @NotBlank String email, @NotBlank String passwordHash, @NotBlank String role) {
}
