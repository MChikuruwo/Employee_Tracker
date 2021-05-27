package com.employeetracker.EmployeeTrackerAPI.dto;

import com.employeetracker.EmployeeTrackerAPI.enums.TaskRequestAction;
import com.employeetracker.EmployeeTrackerAPI.enums.TaskRequestStatus;
import lombok.Data;

import java.sql.Date;

@Data
public class AddTaskRequestDto {
    private String taskName;
    private String description;
    private TaskRequestStatus taskRequestStatus;
    private TaskRequestAction taskRequestAction;
    private Date startDate;
    private Date endDate;
}
