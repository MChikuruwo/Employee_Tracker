package com.employeetracker.EmployeeTrackerAPI.dto;

public class UpdateBusinessUnitDto {
    private Integer id;
    private String businessUnit;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBusinessUnit() {
        return businessUnit;
    }

    public void setBusinessUnit(String businessUnit) {
        this.businessUnit = businessUnit;
    }
}
