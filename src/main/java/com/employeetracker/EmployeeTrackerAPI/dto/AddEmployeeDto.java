package com.employeetracker.EmployeeTrackerAPI.dto;


import com.employeetracker.EmployeeTrackerAPI.enums.Gender;
import com.employeetracker.EmployeeTrackerAPI.enums.ResidentialStatus;
import lombok.Data;

@Data
public class AddEmployeeDto {
    private String employeeCode;
    private String name;
    private String surname;
    private Gender gender;
    private String mobileNumber;
    private String emailAddress;
    private ResidentialStatus residentialStatus;
    private String address1;


}
