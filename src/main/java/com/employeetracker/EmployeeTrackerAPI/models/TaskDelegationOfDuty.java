package com.employeetracker.EmployeeTrackerAPI.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "task_delegation_of_duty", schema = "employee_tracker")
public class TaskDelegationOfDuty {
    private Long id;
    private Task taskByTaskId;
    private DelegationOfDuty delegationOfDutyByDelegationOfDutyId;

    @Id
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
        TaskDelegationOfDuty that = (TaskDelegationOfDuty) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @ManyToOne
    @JoinColumn(name = "task_id", referencedColumnName = "id", nullable = false)
    public Task getTaskByTaskId() {
        return taskByTaskId;
    }

    public void setTaskByTaskId(Task taskByTaskId) {
        this.taskByTaskId = taskByTaskId;
    }

    @ManyToOne
    @JoinColumn(name = "delegation_of_duty_id", referencedColumnName = "id", nullable = false)
    public DelegationOfDuty getDelegationOfDutyByDelegationOfDutyId() {
        return delegationOfDutyByDelegationOfDutyId;
    }

    public void setDelegationOfDutyByDelegationOfDutyId(DelegationOfDuty delegationOfDutyByDelegationOfDutyId) {
        this.delegationOfDutyByDelegationOfDutyId = delegationOfDutyByDelegationOfDutyId;
    }
}
