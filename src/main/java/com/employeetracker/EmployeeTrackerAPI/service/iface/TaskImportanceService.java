package com.employeetracker.EmployeeTrackerAPI.service.iface;

import com.employeetracker.EmployeeTrackerAPI.models.TaskImportance;

import java.util.List;

public interface TaskImportanceService {
    List<TaskImportance> getAll();
    TaskImportance getOne(Long id);
    TaskImportance findByImportance(String importance);
}
