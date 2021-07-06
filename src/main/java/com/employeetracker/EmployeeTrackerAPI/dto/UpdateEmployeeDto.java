package com.employeetracker.EmployeeTrackerAPI.dto;


import com.employeetracker.EmployeeTrackerAPI.enums.Gender;
import com.employeetracker.EmployeeTrackerAPI.enums.ResidentialStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateEmployeeDto {
    private Integer id;
    private String employeeCode;
    private String name;
    private String surname;
    private LocalDate dateOfBirth;
    private Gender gender;
    private String mobileNumber;
    private String emailAddress;
    private ResidentialStatus residentialStatus;
    private String address1;

}
