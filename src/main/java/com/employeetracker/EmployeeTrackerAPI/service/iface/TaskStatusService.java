package com.employeetracker.EmployeeTrackerAPI.service.iface;

import com.employeetracker.EmployeeTrackerAPI.models.TaskStatus;

import java.util.List;

public interface TaskStatusService {
    List<TaskStatus> getAll();
    TaskStatus getOne(Long id);
    TaskStatus findByStatus(String status);
}
