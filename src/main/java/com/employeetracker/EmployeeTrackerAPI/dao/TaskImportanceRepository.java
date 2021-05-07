package com.employeetracker.EmployeeTrackerAPI.dao;

import com.employeetracker.EmployeeTrackerAPI.models.TaskImportance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskImportanceRepository extends JpaRepository<TaskImportance,Long> {
    TaskImportance findByImportance(String importance);
}
