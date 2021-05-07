package com.employeetracker.EmployeeTrackerAPI.dao;

import com.employeetracker.EmployeeTrackerAPI.models.Employee;
import com.employeetracker.EmployeeTrackerAPI.models.EmployeeStatus;
import com.employeetracker.EmployeeTrackerAPI.models.JobTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Employee findByUserId(Integer userId);
    Employee findByEmployeeCode(String employeeCode);
    List<Employee> findAllByEmployeeStatus(EmployeeStatus employeeStatus);
    List<Employee> findAllByJobTitle(JobTitle jobTitle);
    List<Employee> findAllByResidentialStatus(String residentialStatus);
    List<Employee> findAllByGender(String gender);
    Employee findEmployeeByEmployeeCode(String employeeCode);
    Employee findByName(String name);

}
