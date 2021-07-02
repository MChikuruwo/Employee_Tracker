package com.employeetracker.EmployeeTrackerAPI.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class AddTaskRequestDto {
    private String taskName;
    private String description;
    private Date startDate;
    private Date endDate;
}
