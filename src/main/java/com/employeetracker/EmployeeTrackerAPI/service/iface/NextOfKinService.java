package com.employeetracker.EmployeeTrackerAPI.service.iface;

import com.employeetracker.EmployeeTrackerAPI.models.Employee;
import com.employeetracker.EmployeeTrackerAPI.models.NextOfKin;

import java.util.List;

public interface NextOfKinService {
    String add(NextOfKin nextOfKin);
    String update(NextOfKin nextOfKin);
    String delete(Long id);
    List<NextOfKin> getAll();
    NextOfKin getOne(Long id);
    NextOfKin findByEmployee(Employee employee);
}
