package com.employeetracker.EmployeeTrackerAPI.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "task_importance", schema = "employee_tracker")
public class TaskImportance {
    private Long id;
    private String importance;

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
    @Column(name = "importance")
    public String getImportance() {
        return importance;
    }

    public void setImportance(String importance) {
        this.importance = importance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskImportance that = (TaskImportance) o;
        return Objects.equals(id, that.id) && Objects.equals(importance, that.importance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, importance);
    }
}


