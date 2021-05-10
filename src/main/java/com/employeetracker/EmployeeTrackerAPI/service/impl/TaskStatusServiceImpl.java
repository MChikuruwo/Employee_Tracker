package com.employeetracker.EmployeeTrackerAPI.service.impl;

/*import com.employeetracker.EmployeeTrackerAPI.dao.TaskStatusRepository;
import com.employeetracker.EmployeeTrackerAPI.models.TaskImportance;
import com.employeetracker.EmployeeTrackerAPI.models.TaskStatus;
import com.employeetracker.EmployeeTrackerAPI.service.iface.TaskStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TaskStatusServiceImpl implements TaskStatusService {

    private final TaskStatusRepository taskStatusRepository;

    @Autowired
    public TaskStatusServiceImpl(TaskStatusRepository taskStatusRepository) {
        this.taskStatusRepository = taskStatusRepository;
    }

    @Override
    public List<TaskStatus> getAll() {
        return taskStatusRepository.findAll();
    }

    @Override
    public TaskStatus getOne(Long id) {
        Optional<TaskStatus> taskStatus = taskStatusRepository.findById(id);
        if (!taskStatus.isPresent()){
            throw new EntityNotFoundException("Task Status Entity not found!");
        }
        return taskStatus.get();
    }

    @Override
    public TaskStatus findByStatus(String status) {
        TaskStatus taskStatus = taskStatusRepository.findByStatus(status);
        if (taskStatus == null){
            throw new EntityNotFoundException("Task Status: " + status + " not found!");
        }
        return taskStatus;
    }
}

 */
