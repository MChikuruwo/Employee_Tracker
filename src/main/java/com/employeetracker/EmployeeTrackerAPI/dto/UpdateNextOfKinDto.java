package com.employeetracker.EmployeeTrackerAPI.dto;

import lombok.Data;

@Data
public class UpdateNextOfKinDto {
    private Long id;
    private String name;
    private String surname;
    private String address;
    private String mobileNumber;
    private String emailAddress;
}
