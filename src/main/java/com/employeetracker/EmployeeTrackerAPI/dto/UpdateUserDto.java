package com.employeetracker.EmployeeTrackerAPI.dto;

import lombok.Data;

@Data
public class UpdateUserDto {
    private Integer id;
    private String name;
    private String surname;
    private String emailAddress;
    private String employeeCode;
    private String password;
    private Boolean isActive;

}
