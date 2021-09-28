package com.employeetracker.EmployeeTrackerAPI.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "task_request_duty", schema = "employee_tracker")
public class TaskRequestDuty {
    private Long id;
    private TaskRequests taskRequestsByTaskRequestId;
    private DelegationOfDuty delegationOfDutyByDelegationOfDutyId;

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
        TaskRequestDuty that = (TaskRequestDuty) o;
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
    @JoinColumn(name = "delegation_of_duty_id", referencedColumnName = "id", nullable = false)
    public DelegationOfDuty getDelegationOfDutyByDelegationOfDutyId() {
        return delegationOfDutyByDelegationOfDutyId;
    }

    public void setDelegationOfDutyByDelegationOfDutyId(DelegationOfDuty delegationOfDutyByDelegationOfDutyId) {
        this.delegationOfDutyByDelegationOfDutyId = delegationOfDutyByDelegationOfDutyId;
    }
}
