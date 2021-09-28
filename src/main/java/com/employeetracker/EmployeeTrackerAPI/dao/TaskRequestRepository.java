package com.employeetracker.EmployeeTrackerAPI.dao;

import com.employeetracker.EmployeeTrackerAPI.models.DelegationOfDuty;
import com.employeetracker.EmployeeTrackerAPI.models.Employee;
import com.employeetracker.EmployeeTrackerAPI.models.TaskRequests;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRequestRepository  extends JpaRepository<TaskRequests,Long> {
    TaskRequests findByTaskName(String taskName);
    List<TaskRequests> findAllByDelegatedDuty(DelegationOfDuty delegatedDuty);
}
