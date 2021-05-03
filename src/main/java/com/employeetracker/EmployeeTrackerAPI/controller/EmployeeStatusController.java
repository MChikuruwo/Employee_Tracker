package com.employeetracker.EmployeeTrackerAPI.controller;

import com.employeetracker.EmployeeTrackerAPI.service.iface.EmployeeStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeStatusController {

    private final EmployeeStatusService employeeStatusService;

    @Autowired
    public EmployeeStatusController(EmployeeStatusService employeeStatusService) {
        this.employeeStatusService = employeeStatusService;
    }
}
