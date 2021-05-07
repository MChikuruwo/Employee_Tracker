package com.employeetracker.EmployeeTrackerAPI.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "delegation_of_duty_task", schema = "employee_tracker")
public class DelegationOfDutyTask {
    private Long id;
    private DelegationOfDuty delegatedDuty;
    private Task task;

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
        DelegationOfDutyTask that = (DelegationOfDutyTask) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    @ManyToOne
    @JoinColumn(name = "delegation_of_duty_id", referencedColumnName = "id", nullable = false)
    public DelegationOfDuty getDelegatedDuty() {
        return delegatedDuty;
    }

    public void setDelegatedDuty(DelegationOfDuty delegatedDuty) {
        this.delegatedDuty = delegatedDuty;
    }



    @ManyToOne
    @JoinColumn(name = "task_id", referencedColumnName = "id", nullable = false)
    public Task getTask() {
        return task;
    }

    public void setTask(Task taskByTaskId) {
        this.task = taskByTaskId;
    }
}
