package com.employeetracker.EmployeeTrackerAPI.dto;

import lombok.Data;

@Data
public class AddSpouseDetailsDto {
    private String name;
    private String surname;
    private String nationalIdNumber;
    private String employer;
    private String employerAddress;
    private String mobileNumber;
    private String emailAddress;


}
