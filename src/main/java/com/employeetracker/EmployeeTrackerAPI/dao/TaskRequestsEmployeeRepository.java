package com.employeetracker.EmployeeTrackerAPI.dao;

import com.employeetracker.EmployeeTrackerAPI.models.TaskRequests;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRequestsEmployeeRepository extends JpaRepository<TaskRequests,Long> {
}
