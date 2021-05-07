package com.employeetracker.EmployeeTrackerAPI.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class UpdateTaskDto {
    private Long id;
    private String name;
    private String description;
    private Date startDate;
    private Date dueDate;
    private Date actualStartDate;
    private Date actualEndDate;
    private String reasonSOfMissingDueDate;
}
