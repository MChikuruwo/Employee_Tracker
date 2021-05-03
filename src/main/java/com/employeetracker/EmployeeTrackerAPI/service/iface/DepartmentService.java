package com.employeetracker.EmployeeTrackerAPI.service.iface;

import com.employeetracker.EmployeeTrackerAPI.models.Department;

import java.util.List;

public interface DepartmentService {
    String add(Department department);
    String update(Department department);
    String delete(Integer id);
    List<Department> getAll();
    Department getOne(Integer id);
    Department findByDepartment(String department);
}
