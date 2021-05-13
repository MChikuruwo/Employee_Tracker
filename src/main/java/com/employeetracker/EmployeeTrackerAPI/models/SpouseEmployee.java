package com.employeetracker.EmployeeTrackerAPI.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "spouse_employee", schema = "employee_tracker")
public class SpouseEmployee {
    private Long id;
    private SpouseDetails spouseDetailsBySpouseId;
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
        SpouseEmployee that = (SpouseEmployee) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @ManyToOne
    @JoinColumn(name = "spouse_id", referencedColumnName = "id", nullable = false)
    public SpouseDetails getSpouseDetailsBySpouseId() {
        return spouseDetailsBySpouseId;
    }

    public void setSpouseDetailsBySpouseId(SpouseDetails spouseDetailsBySpouseId) {
        this.spouseDetailsBySpouseId = spouseDetailsBySpouseId;
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
