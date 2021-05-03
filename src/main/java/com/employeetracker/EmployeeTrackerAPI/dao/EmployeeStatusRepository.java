package com.employeetracker.EmployeeTrackerAPI.dao;

import com.employeetracker.EmployeeTrackerAPI.models.EmployeeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeStatusRepository extends JpaRepository<EmployeeStatus, Long> {
    EmployeeStatus findByStatus(String status);
}
