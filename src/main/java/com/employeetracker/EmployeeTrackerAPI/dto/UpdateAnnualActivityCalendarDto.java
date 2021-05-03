package com.employeetracker.EmployeeTrackerAPI.dto;

import java.sql.Date;

public class UpdateAnnualActivityCalendarDto {
    private Long id;
    private String activity;
    private Date activityDay;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public Date getActivityDay() {
        return activityDay;
    }

    public void setActivityDay(Date activityDay) {
        this.activityDay = activityDay;
    }
}
