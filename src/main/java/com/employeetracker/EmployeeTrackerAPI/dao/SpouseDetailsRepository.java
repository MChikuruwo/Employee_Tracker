package com.employeetracker.EmployeeTrackerAPI.dao;

import com.employeetracker.EmployeeTrackerAPI.models.Employee;
import com.employeetracker.EmployeeTrackerAPI.models.SpouseDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface SpouseDetailsRepository extends JpaRepository<SpouseDetails, Long> {
    SpouseDetails findByEmployeeByEmployeeId(Employee employee);
}
