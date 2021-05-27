package com.employeetracker.EmployeeTrackerAPI.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "task_request_employee", schema = "employee_tracker")
public class TaskRequestEmployee {
    private Long id;
    private TaskRequests taskRequestsByTaskRequestId;
    private Employee employee;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskRequestEmployee that = (TaskRequestEmployee) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @ManyToOne
    @JoinColumn(name = "task_request_id", referencedColumnName = "id", nullable = false)
    public TaskRequests getTaskRequestsByTaskRequestId() {
        return taskRequestsByTaskRequestId;
    }

    public void setTaskRequestsByTaskRequestId(TaskRequests taskRequestsByTaskRequestId) {
        this.taskRequestsByTaskRequestId = taskRequestsByTaskRequestId;
    }

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id", nullable = false)
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
