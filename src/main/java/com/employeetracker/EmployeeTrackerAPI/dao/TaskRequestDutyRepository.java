package com.employeetracker.EmployeeTrackerAPI.dao;

import com.employeetracker.EmployeeTrackerAPI.models.TaskRequests;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRequestDutyRepository extends JpaRepository<TaskRequests,Long> {
}
