package com.employeetracker.EmployeeTrackerAPI.dto;

import lombok.Data;

@Data
public class AddNextOfKinDto {
    private String name;
    private String surname;
    private String address;
    private String mobileNumber;
    private String emailAddress;
}
