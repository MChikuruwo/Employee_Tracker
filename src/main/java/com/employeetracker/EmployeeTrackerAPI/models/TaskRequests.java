package com.employeetracker.EmployeeTrackerAPI.models;

import com.employeetracker.EmployeeTrackerAPI.enums.TaskProgress;
import com.employeetracker.EmployeeTrackerAPI.enums.TaskRequestAction;
import com.employeetracker.EmployeeTrackerAPI.enums.TaskRequestStatus;
import com.employeetracker.EmployeeTrackerAPI.enums.TaskStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "task_requests", schema = "employee_tracker")
public class TaskRequests {
    private Long id;
    private String taskName;
    private String description;
    private TaskRequestStatus taskRequestStatus;
    private TaskRequestAction taskRequestAction;
    private TaskStatus taskStatus;
    private TaskProgress taskProgress;
    private Date startDate;
    private Date endDate;
    private Timestamp dateUpdated;
    private TaskImportance importance;

    private DelegationOfDuty duty;
    private  Set<Employee> subordinate;

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
    @Column(name = "task_name")
    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
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
    @Enumerated(EnumType.STRING)
    @Column(name = "task_request_status")
    public TaskRequestStatus getTaskRequestStatus() {
        return taskRequestStatus;
    }

    public void setTaskRequestStatus(TaskRequestStatus taskRequestStatus) {
        this.taskRequestStatus = taskRequestStatus;
    }

    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "task_request_action")
    public TaskRequestAction getTaskRequestAction() {
        return taskRequestAction;
    }

    public void setTaskRequestAction(TaskRequestAction taskRequestAction) {
        this.taskRequestAction = taskRequestAction;
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
    @Enumerated(EnumType.STRING)
    @Column(name = "task_progress")
    public TaskProgress getTaskProgress() {
        return taskProgress;
    }

    public void setTaskProgress(TaskProgress taskProgress) {
        this.taskProgress = taskProgress;
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
    @Column(name = "end_date")
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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
        TaskRequests that = (TaskRequests) o;
        return Objects.equals(id, that.id) && Objects.equals(taskName, that.taskName) && Objects.equals(description, that.description) && Objects.equals(taskRequestStatus, that.taskRequestStatus) && Objects.equals(taskRequestAction, that.taskRequestAction) && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate) && Objects.equals(dateUpdated, that.dateUpdated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, taskName, description, taskRequestStatus, taskRequestAction, startDate, endDate, dateUpdated);
    }

    @ManyToOne
    @JoinColumn(name = "task_importance_id", referencedColumnName = "id", nullable = false)
    public TaskImportance getImportance() {
        return importance;
    }

    public void setImportance(TaskImportance importance) {
        this.importance = importance;
    }

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "task_request_duty", joinColumns = @JoinColumn(name = "task_request_id"), inverseJoinColumns = @JoinColumn(name = "delegation_of_duty_id"))
    public DelegationOfDuty getDelegatedDuty() {
        return delegatedDuty;
    }

    public void setDelegatedDuty(DelegationOfDuty delegatedDuty) {
        this.delegatedDuty = delegatedDuty;
    }

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "task_request_employee", joinColumns = @JoinColumn(name = "task_request_id"), inverseJoinColumns = @JoinColumn(name = "employee_id"))
    public Set<Employee> getSubordinate() {
        return subordinate;
    }

    public void setSubordinate(Set<Employee> subordinate) {
        this.subordinate = subordinate;
    }
}
