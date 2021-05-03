package com.employeetracker.EmployeeTrackerAPI.models;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "job_title", schema = "employee_tracker")
public class JobTitle {
    private Long id;
    private String titleName;
    private Timestamp dateCreated;
    private Timestamp dateUpdated;
    private BusinessUnit businessUnit;
    private Department department;

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
    @Column(name = "title_name")
    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    @Basic
    @CreationTimestamp
    @Column(name = "date_created")
    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Basic
    @UpdateTimestamp
    @Column(name = "date_updated")
    public Timestamp getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Timestamp dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobTitle that = (JobTitle) o;
        return Objects.equals(id, that.id) && Objects.equals(titleName, that.titleName) && Objects.equals(dateCreated, that.dateCreated) && Objects.equals(dateUpdated, that.dateUpdated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id,titleName, dateCreated, dateUpdated);
    }

    @ManyToOne
    @JoinColumn(name = "business_unit_id", referencedColumnName = "id", nullable = false)
    public BusinessUnit getBusinessUnit() {
        return businessUnit;
    }

    public void setBusinessUnit(BusinessUnit businessUnitByBusinessUnitId) {
        this.businessUnit = businessUnitByBusinessUnitId;
    }

    @ManyToOne
    @JoinColumn(name = "department_id", referencedColumnName = "id", nullable = false)
    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department departmentByDepartmentId) {
        this.department = departmentByDepartmentId;
    }
}
