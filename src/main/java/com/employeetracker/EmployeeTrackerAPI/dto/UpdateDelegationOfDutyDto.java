package com.employeetracker.EmployeeTrackerAPI.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class UpdateDelegationOfDutyDto {
    private Long id;
    private String duty;
    private Date fromDate;
    private Date toDate;
    private String reason;

}
