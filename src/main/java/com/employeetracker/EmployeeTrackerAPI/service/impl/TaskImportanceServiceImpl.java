package com.employeetracker.EmployeeTrackerAPI.service.impl;

import com.employeetracker.EmployeeTrackerAPI.dao.TaskImportanceRepository;
import com.employeetracker.EmployeeTrackerAPI.models.Role;
import com.employeetracker.EmployeeTrackerAPI.models.TaskImportance;
import com.employeetracker.EmployeeTrackerAPI.service.iface.TaskImportanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TaskImportanceServiceImpl implements TaskImportanceService {

    private final TaskImportanceRepository taskImportanceRepository;

    @Autowired
    public TaskImportanceServiceImpl(TaskImportanceRepository taskImportanceRepository) {
        this.taskImportanceRepository = taskImportanceRepository;
    }

    @Override
    public List<TaskImportance> getAll() {
        return taskImportanceRepository.findAll();
    }

    @Override
    public TaskImportance getOne(Long id) {
        Optional<TaskImportance> taskImportance = taskImportanceRepository.findById(id);
        if (!taskImportance.isPresent()){
            throw new EntityNotFoundException("Task Importance Entity not found!");
        }
        return taskImportance.get();
    }

    @Override
    public TaskImportance findByImportance(String importance) {
        TaskImportance taskImportance = taskImportanceRepository.findByImportance(importance);
        if (taskImportance == null){
            throw new EntityNotFoundException("Task Importance " + importance + " not found!");
        }
        return taskImportance;
    }

}
