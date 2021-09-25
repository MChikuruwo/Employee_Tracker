package com.employeetracker.EmployeeTrackerAPI.dto;

import lombok.Data;

@Data
public class AddUserDto {
    private String name;
    private String surname;
    private String emailAddress;
    private String employeeCode;
    private Integer roleId;

}
