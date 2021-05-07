package com.employeetracker.EmployeeTrackerAPI.dao;

import com.employeetracker.EmployeeTrackerAPI.models.Employee;
import com.employeetracker.EmployeeTrackerAPI.models.Task;
import com.employeetracker.EmployeeTrackerAPI.models.TaskImportance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Task findByName(String name);
    List<Task> findAllByAssignedTo(Employee employee);
    List<Task> findTaskByImportance(TaskImportance importance);
   // List<Task> findTasksByTaskStatus(TaskStatus taskStatus);
    Task
}
