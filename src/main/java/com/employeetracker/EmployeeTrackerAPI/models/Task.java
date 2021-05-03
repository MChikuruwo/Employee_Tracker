package com.employeetracker.EmployeeTrackerAPI.models;

import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "task", schema = "employee_tracker")
public class Task {
    private Long id;
    private String name;
    private String description;
    private Timestamp startTime;
    private Timestamp endTime;
    private Timestamp dateUpdated;
    private Employee assignedTo;
    private TaskImportance taskImportanceByActivityImportance;
    private TaskStatus taskStatusByActivityStatus;
    private TaskRequests taskRequestsByActivityRequests;

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
    @Column(name = "start_time")
    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    @Basic
    @Column(name = "end_time")
    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
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
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(startTime, that.startTime) && Objects.equals(endTime, that.endTime) && Objects.equals(dateUpdated, that.dateUpdated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, startTime, endTime, dateUpdated);
    }

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id", nullable = false)
    public Employee getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(Employee employeeByEmployeeId) {
        this.assignedTo = employeeByEmployeeId;
    }

    @ManyToOne
    @JoinColumn(name = "task_importance", referencedColumnName = "id", nullable = false)
    public TaskImportance getTaskImportanceByActivityImportance() {
        return taskImportanceByActivityImportance;
    }

    public void setTaskImportanceByActivityImportance(TaskImportance taskImportanceByActivityImportance) {
        this.taskImportanceByActivityImportance = taskImportanceByActivityImportance;
    }

    @ManyToOne
    @JoinColumn(name = "task_status", referencedColumnName = "id", nullable = false)
    public TaskStatus getTaskStatusByActivityStatus() {
        return taskStatusByActivityStatus;
    }

    public void setTaskStatusByActivityStatus(TaskStatus taskStatusByActivityStatus) {
        this.taskStatusByActivityStatus = taskStatusByActivityStatus;
    }

    @ManyToOne
    @JoinColumn(name = "task_requests", referencedColumnName = "id", nullable = false)
    public TaskRequests getTaskRequestsByActivityRequests() {
        return taskRequestsByActivityRequests;
    }

    public void setTaskRequestsByActivityRequests(TaskRequests taskRequestsByActivityRequests) {
        this.taskRequestsByActivityRequests = taskRequestsByActivityRequests;
    }
}
