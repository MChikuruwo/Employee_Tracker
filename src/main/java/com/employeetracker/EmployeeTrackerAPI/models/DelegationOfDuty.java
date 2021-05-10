package com.employeetracker.EmployeeTrackerAPI.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "delegation_of_duty", schema = "employee_tracker")
public class DelegationOfDuty {
    private Long id;
    private String duty;
    private Date fromDate;
    private Date toDate;
    private String reason;
    private Timestamp dateCreated;
    private Employee employeeByAssignedBy;
    private Employee employeeByAssignTo;

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
    @Column(name = "duty")
    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    @Basic
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
    @Column(name = "from_date")
    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    @Basic
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
    @Column(name = "to_date")
    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    @Basic
    @Column(name = "reason")
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Basic
    @CreationTimestamp
    @Column(name = "date_created")
    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DelegationOfDuty that = (DelegationOfDuty) o;
        return Objects.equals(id, that.id) && Objects.equals(duty, that.duty) && Objects.equals(fromDate, that.fromDate) && Objects.equals(toDate, that.toDate) && Objects.equals(reason, that.reason) && Objects.equals(dateCreated, that.dateCreated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, duty, fromDate, toDate, reason, dateCreated);
    }

    @ManyToOne
    @JoinColumn(name = "assigned_by", referencedColumnName = "id", nullable = false)
    public Employee getEmployeeByAssignedBy() {
        return employeeByAssignedBy;
    }

    public void setEmployeeByAssignedBy(Employee employeeByAssignedBy) {
        this.employeeByAssignedBy = employeeByAssignedBy;
    }

    @ManyToOne
    @JoinColumn(name = "assign_to", referencedColumnName = "id", nullable = false)
    public Employee getEmployeeByAssignTo() {
        return employeeByAssignTo;
    }

    public void setEmployeeByAssignTo(Employee employeeByAssignTo) {
        this.employeeByAssignTo = employeeByAssignTo;
    }
}
