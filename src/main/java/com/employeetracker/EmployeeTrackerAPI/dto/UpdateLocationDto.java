package com.employeetracker.EmployeeTrackerAPI.dto;

import lombok.Data;

@Data
public class UpdateLocationDto {
    private Long id;
    private String longitude;
    private String latitude;
}
