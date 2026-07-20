package com.easur.dispatch.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "dispatch_service_task")
public class DispatchTaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String incidentReference;
    private String assignment;
    private String status;
    private Instant createdAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getIncidentReference() { return incidentReference; }
    public void setIncidentReference(String incidentReference) { this.incidentReference = incidentReference; }

    public String getAssignment() { return assignment; }
    public void setAssignment(String assignment) { this.assignment = assignment; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
