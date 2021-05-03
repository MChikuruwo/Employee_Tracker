package com.employeetracker.EmployeeTrackerAPI.service.iface;


import com.employeetracker.EmployeeTrackerAPI.models.Employee;
import com.employeetracker.EmployeeTrackerAPI.models.EmployeeStatus;
import com.employeetracker.EmployeeTrackerAPI.models.JobTitle;
import com.employeetracker.EmployeeTrackerAPI.models.User;

import java.util.List;

public interface EmployeeService {
    String add(Employee employee);
    String update(Employee employee);
    String delete(Long id);
    List<Employee> getAll();
    Employee getOne(Long id);
    User empCodeAuth(String employeeCode, String password) throws Exception;

    Employee findByUserId(Integer userId);
    Employee findByEmployeeCode(String employeeCode);
    List<Employee> findAllByEmployeeStatus(EmployeeStatus employeeStatus);
    List<Employee> findAllByJobTitle(JobTitle jobTitle);
    List<Employee> findAllByResidentialStatus(String residentialStatus);
    List<Employee> findAllByGender(String gender);
}
