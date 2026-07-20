package com.easur.telephony.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateRequest(@NotBlank String caller, @NotBlank String destination) {}
