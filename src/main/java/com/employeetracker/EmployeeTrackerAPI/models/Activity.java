package com.employeetracker.EmployeeTrackerAPI.models;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "activity", schema = "employee_tracker")
public class Activity {
    private Long id;
    private Long entityId;
    private String narrative;
    private Timestamp dateAdded;
    private AuditTrail auditTrail;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "entity_id")
    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    @Basic
    @Column(name = "narrative")
    public String getNarrative() {
        return narrative;
    }

    public void setNarrative(String narrative) {
        this.narrative = narrative;
    }

    @Basic
    @CreationTimestamp
    @Column(name = "date_added")
    public Timestamp getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Timestamp dateAdded) {
        this.dateAdded = dateAdded;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Activity that = (Activity) o;
        return Objects.equals(id, that.id) && Objects.equals(entityId, that.entityId) && Objects.equals(narrative, that.narrative) && Objects.equals(dateAdded, that.dateAdded);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, entityId, narrative, dateAdded);
    }

    @ManyToOne
    @JoinColumn(name = "audit_trail_id", referencedColumnName = "id", nullable = false)
    public AuditTrail getAuditTrail() {
        return auditTrail;
    }

    public void setAuditTrail(AuditTrail auditTrailByAuditTrailId) {
        this.auditTrail = auditTrailByAuditTrailId;
    }
}
