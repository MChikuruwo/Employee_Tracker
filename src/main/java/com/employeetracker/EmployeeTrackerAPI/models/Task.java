package com.employeetracker.EmployeeTrackerAPI.models;

import com.employeetracker.EmployeeTrackerAPI.enums.TaskStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "task", schema = "employee_tracker")
public class Task {
    private Long id;
    private String name;
    private String description;
    private Date startDate;
    private Date dueDate;
    private Date actualStartDate;
    private Date actualEndDate;
    private String reasonSOfMissingDueDate;
    private Timestamp dateUpdated;
    private TaskImportance importance;
    private TaskStatus taskStatus;

    private Set<Employee> assignedTo;

    private DelegationOfDuty delegatedDuty;


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
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
    @Column(name = "start_date")
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Basic
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
    @Column(name = "due_date")
    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    @Basic
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
    @Column(name = "actual_start_date")
    public Date getActualStartDate() {
        return actualStartDate;
    }

    public void setActualStartDate(Date actualStartDate) {
        this.actualStartDate = actualStartDate;
    }

    @Basic
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
    @Column(name = "actual_end_date")
    public Date getActualEndDate() {
        return actualEndDate;
    }

    public void setActualEndDate(Date actualEndDate) {
        this.actualEndDate = actualEndDate;
    }

    @Basic
    @Column(name = "reason_of_missing_due_date")
    public String getReasonSOfMissingDueDate() {
        return reasonSOfMissingDueDate;
    }

    public void setReasonSOfMissingDueDate(String reasonSOfMissingDueDate) {
        this.reasonSOfMissingDueDate = reasonSOfMissingDueDate;
    }


    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "task_status")
    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    @Basic
    @UpdateTimestamp
    @Column(name = "date_updated")
    public Timestamp getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Timestamp dateUpdated) {
        this.dateUpdated = dateUpdated;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task that = (Task) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(description, that.description)  && Objects.equals(dateUpdated, that.dateUpdated);
    }

    public Task() {
        super();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "task_employee", joinColumns = @JoinColumn(name = "task_id"), inverseJoinColumns = @JoinColumn(name = "employee_id"))
    public Set<Employee> getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(Set<Employee> assignedTo) {
        this.assignedTo = assignedTo;
    }

    @ManyToOne
    @JoinColumn(name = "task_importance", referencedColumnName = "id", nullable = false)
    public TaskImportance getImportance() {
        return importance;
    }

    public void setImportance(TaskImportance taskImportanceByActivityImportance) {
        this.importance = taskImportanceByActivityImportance;
    }

    /*@ManyToOne
    @JoinColumn(name = "task_status", referencedColumnName = "id", nullable = false)
    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatusByActivityStatus) {
        this.taskStatus = taskStatusByActivityStatus;
    }

     */


    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "task_delegation_of_duty", joinColumns = @JoinColumn(name = "task_id"), inverseJoinColumns = @JoinColumn(name = "delegation_of_duty_id"))
    public DelegationOfDuty getDelegatedDuty() {
        return delegatedDuty;
    }

    public void setDelegatedDuty(DelegationOfDuty delegatedDuty) {
        this.delegatedDuty = delegatedDuty;
    }
}
