package com.easur.incident.dto;

public record ItemResponse(Long id, String reference, String title, String severity, String status) {
}
