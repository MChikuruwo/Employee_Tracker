package com.employeetracker.EmployeeTrackerAPI.dto;

public class UpdateDepartmentDto {
    private Integer id;
    private String department;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
