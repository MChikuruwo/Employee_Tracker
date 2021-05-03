package com.employeetracker.EmployeeTrackerAPI.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "annual_activity_category", schema = "employee_tracker")
public class AnnualActivityCategory {
    private Long id;
    private String category;

    @Id
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "category")
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnnualActivityCategory that = (AnnualActivityCategory) o;
        return Objects.equals(id, that.id) && Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, category);
    }
}
