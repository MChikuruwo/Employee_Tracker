package com.employeetracker.EmployeeTrackerAPI.dto;

import com.employeetracker.EmployeeTrackerAPI.enums.TaskProgress;
import com.employeetracker.EmployeeTrackerAPI.enums.TaskRequestAction;
import com.employeetracker.EmployeeTrackerAPI.enums.TaskRequestStatus;
import com.employeetracker.EmployeeTrackerAPI.enums.TaskStatus;
import lombok.Data;

import java.sql.Date;

@Data
public class UpdateTaskRequestDto {
    private Long id;
    private String taskName;
    private String description;
    private TaskRequestStatus taskRequestStatus;
    private TaskRequestAction taskRequestAction;
    private TaskStatus taskStatus;
    private TaskProgress taskProgress;
    private Date startDate;
    private Date endDate;
    private Long dutyId;
    private Integer employeeId;
}
