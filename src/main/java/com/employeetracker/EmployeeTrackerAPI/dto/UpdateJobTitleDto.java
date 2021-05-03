package com.employeetracker.EmployeeTrackerAPI.dto;

public class UpdateJobTitleDto {
    private Long id;
    private String titleName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }


}
