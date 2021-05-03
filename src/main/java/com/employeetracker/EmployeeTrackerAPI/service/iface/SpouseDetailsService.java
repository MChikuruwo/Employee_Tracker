package com.employeetracker.EmployeeTrackerAPI.service.iface;

import com.employeetracker.EmployeeTrackerAPI.models.Employee;
import com.employeetracker.EmployeeTrackerAPI.models.SpouseDetails;

import java.util.List;

public interface SpouseDetailsService {
    String add(SpouseDetails spouseDetails);
    String update(SpouseDetails spouseDetails);
    String delete(Long id);
    List<SpouseDetails> getAll();
    SpouseDetails getOne(Long id);
    SpouseDetails findByEmployee(Employee employee);
}
