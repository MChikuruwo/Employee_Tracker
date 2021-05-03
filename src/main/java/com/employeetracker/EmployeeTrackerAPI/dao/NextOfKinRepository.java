package com.employeetracker.EmployeeTrackerAPI.dao;

import com.employeetracker.EmployeeTrackerAPI.models.Employee;
import com.employeetracker.EmployeeTrackerAPI.models.NextOfKin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface NextOfKinRepository extends JpaRepository<NextOfKin, Long> {
    NextOfKin findByEmployee(Employee employee);
}
