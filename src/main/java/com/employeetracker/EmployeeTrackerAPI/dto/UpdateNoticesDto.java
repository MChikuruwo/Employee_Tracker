package com.employeetracker.EmployeeTrackerAPI.dto;

public class UpdateNoticesDto {
    private Long id;
    private String notice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }
}
