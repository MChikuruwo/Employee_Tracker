package com.employeetracker.EmployeeTrackerAPI.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class AddTaskDto {
    private String name;
    private String description;
    private Date startDate;
    private Date dueDate;
    private Date actualStartDate;
    private Date actualEndDate;
    private String reasonSOfMissingDueDate;
    private Long dutyId;
    private Integer employeeId;
    private Long taskImportanceId;

}
