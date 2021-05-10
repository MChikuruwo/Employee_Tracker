package com.employeetracker.EmployeeTrackerAPI.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class AddDelegationOfDutyDto {
    private String duty;
    private Date fromDate;
    private Date toDate;
    private String reason;
}
