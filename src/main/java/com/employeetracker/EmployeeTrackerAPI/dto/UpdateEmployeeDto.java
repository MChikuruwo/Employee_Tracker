package com.employeetracker.EmployeeTrackerAPI.dto;


import com.employeetracker.EmployeeTrackerAPI.enums.Gender;
import com.employeetracker.EmployeeTrackerAPI.enums.ResidentialStatus;
import lombok.Data;

@Data
public class UpdateEmployeeDto {
    private Integer id;
    private Long userId;
    private String employeeCode;
    private String name;
    private String surname;
    private Gender gender;
    private String mobileNumber;
    private String emailAddress;
    private ResidentialStatus residentialStatus;
    private String address1;
    private Double monthlySalary;
    private String accountNumber;

}
