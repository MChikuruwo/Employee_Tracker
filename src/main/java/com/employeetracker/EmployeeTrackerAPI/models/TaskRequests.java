package com.employeetracker.EmployeeTrackerAPI.models;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "task_requests", schema = "employee_tracker")
public class TaskRequests {
    private Long id;
    private Timestamp requestDate;
    private Employee employeeByEmployeeId;
    private Task task;
    private TaskRequestAction taskRequestAction;
    private TaskRequestStatus taskRequestStatus;

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
    @CreationTimestamp
    @Column(name = "request_date")
    public Timestamp getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Timestamp requestDate) {
        this.requestDate = requestDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskRequests that = (TaskRequests) o;
        return Objects.equals(id, that.id) && Objects.equals(requestDate, that.requestDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, requestDate);
    }

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id", nullable = false)
    public Employee getEmployeeByEmployeeId() {
        return employeeByEmployeeId;
    }

    public void setEmployeeByEmployeeId(Employee employeeByEmployeeId) {
        this.employeeByEmployeeId = employeeByEmployeeId;
    }

    @ManyToOne
    @JoinColumn(name = "task", referencedColumnName = "id", nullable = false)
    public Task getTask() {
        return task;
    }

    public void setTask(Task taskByActivity) {
        this.task = taskByActivity;
    }

    @ManyToOne
    @JoinColumn(name = "task_request_action", referencedColumnName = "id", nullable = false)
    public TaskRequestAction getTaskRequestAction() {
        return taskRequestAction;
    }

    public void setTaskRequestAction(TaskRequestAction taskRequestActionByActivityRequestAction) {
        this.taskRequestAction = taskRequestActionByActivityRequestAction;
    }

    @ManyToOne
    @JoinColumn(name = "task_request_status", referencedColumnName = "id", nullable = false)
    public TaskRequestStatus getTaskRequestStatus() {
        return taskRequestStatus;
    }

    public void setTaskRequestStatus(TaskRequestStatus taskRequestStatusByActivityRequestStatus) {
        this.taskRequestStatus = taskRequestStatusByActivityRequestStatus;
    }
}
