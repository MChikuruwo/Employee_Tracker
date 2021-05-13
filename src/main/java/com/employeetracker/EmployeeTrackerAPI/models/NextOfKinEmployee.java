package com.employeetracker.EmployeeTrackerAPI.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "next_of_kin_employee", schema = "employee_tracker")
public class NextOfKinEmployee {
    private Long id;
    private NextOfKin nextOfKinByNextOfKinId;
    private Employee employeeByEmployeeId;

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
        NextOfKinEmployee that = (NextOfKinEmployee) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @ManyToOne
    @JoinColumn(name = "next_of_kin_id", referencedColumnName = "id", nullable = false)
    public NextOfKin getNextOfKinByNextOfKinId() {
        return nextOfKinByNextOfKinId;
    }

    public void setNextOfKinByNextOfKinId(NextOfKin nextOfKinByNextOfKinId) {
        this.nextOfKinByNextOfKinId = nextOfKinByNextOfKinId;
    }

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id", nullable = false)
    public Employee getEmployeeByEmployeeId() {
        return employeeByEmployeeId;
    }

    public void setEmployeeByEmployeeId(Employee employeeByEmployeeId) {
        this.employeeByEmployeeId = employeeByEmployeeId;
    }
}
