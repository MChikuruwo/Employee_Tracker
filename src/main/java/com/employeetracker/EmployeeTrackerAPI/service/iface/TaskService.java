package com.employeetracker.EmployeeTrackerAPI.service.iface;

import com.employeetracker.EmployeeTrackerAPI.enums.TaskStatus;
import com.employeetracker.EmployeeTrackerAPI.models.*;

import java.util.List;

public interface TaskService {
    String add(Task task);
    List<Task> getAll();
    String update(Task task);
    String delete(Long id);
    Task getOne(Long id);

    Task findByName(String name);
    List<Task> findAllByAssignedTo(Employee employee);
    List<Task> findTaskByImportance(TaskImportance importance);

}
