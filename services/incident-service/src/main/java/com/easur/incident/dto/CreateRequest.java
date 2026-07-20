package com.easur.incident.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateRequest(@NotBlank String title, @NotBlank String severity, @NotBlank String address) {
}
