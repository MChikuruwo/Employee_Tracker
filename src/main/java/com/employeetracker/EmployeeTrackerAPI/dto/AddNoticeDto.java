package com.employeetracker.EmployeeTrackerAPI.dto;

public class AddNoticeDto {
    private String title;
    private String notice;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }
}
