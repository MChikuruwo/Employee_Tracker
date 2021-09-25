package com.employeetracker.EmployeeTrackerAPI.dto;

import lombok.Data;

@Data
public class AddJobTitleDto {
    private String titleName;
    private Integer departmentId;
    private Integer businessUnitId;

}
