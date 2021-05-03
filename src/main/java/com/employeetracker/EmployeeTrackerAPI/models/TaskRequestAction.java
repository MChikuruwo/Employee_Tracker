package com.employeetracker.EmployeeTrackerAPI.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "task_request_action", schema = "employee_tracker")
public class TaskRequestAction {
    private Long id;
    private String action;

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
    @Column(name = "action")
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskRequestAction that = (TaskRequestAction) o;
        return Objects.equals(id, that.id) && Objects.equals(action, that.action);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, action);
    }
}
