package com.easur.dispatch.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateRequest(@NotBlank String incidentReference, @NotBlank String assignment) {}
