package com.employeetracker.EmployeeTrackerAPI.service.impl;

import com.employeetracker.EmployeeTrackerAPI.dao.DelegationOfDutyRepository;
import com.employeetracker.EmployeeTrackerAPI.dao.TaskRepository;
import com.employeetracker.EmployeeTrackerAPI.enums.TaskStatus;
import com.employeetracker.EmployeeTrackerAPI.exceptions.UnknownDutyException;
import com.employeetracker.EmployeeTrackerAPI.models.*;
import com.employeetracker.EmployeeTrackerAPI.service.iface.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final DelegationOfDutyRepository delegationOfDutyRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, DelegationOfDutyRepository delegationOfDutyRepository) {
        this.taskRepository = taskRepository;
        this.delegationOfDutyRepository = delegationOfDutyRepository;
    }

    @Override
    public String add(Task task) {
        DelegationOfDuty findDuty = (DelegationOfDuty) task.getDelegatedDuty();
        if (findDuty == null) {
            //set status to pending by default
            task.setTaskStatus(TaskStatus.PENDING);
            throw new UnknownDutyException("No such duty found.");
        } else {
            taskRepository.save(task);
            return "Task Successfully added.";
        }
    }

    @Override
    public List<Task> getAll() {
        List<Task> tasks = taskRepository.findAll();
        if(tasks == null){
            throw  new EntityNotFoundException("Tasks Not Found!");

        }else

        return tasks;
    }

    @Override
    public String update(Task task) {
        Optional<Task> detailsFromDatabase = taskRepository.findById(task.getId());
        if (!detailsFromDatabase.isPresent()) throw new EntityNotFoundException("Task does not exist");
        // Carry date created timestamp
        task.setDateUpdated(detailsFromDatabase.get().getDateUpdated());
        DelegationOfDuty findDuty = (DelegationOfDuty) task.getDelegatedDuty();
        if (findDuty == null) {
            throw new UnknownDutyException("No such task found.");
        } else
            taskRepository.save(task);
        return "Task with ID " + task.getId() + " has been updated";    }

    @Override
    public String delete(Long id) {
        Optional<Task> detailsToDelete = taskRepository.findById(id);
        if (!detailsToDelete.isPresent()){
            throw new EntityNotFoundException("Task with ID " + id + " does not exist");
        }
        taskRepository.deleteById(id);
        return "Task has been successfully deleted.";
    }

    @Override
    public Task getOne(Long id) {
        Optional<Task> task = taskRepository.findById(id);
        if (!task.isPresent()){
            throw new EntityNotFoundException("Task with the ID " + id + " does not exist");
        }
        return task.get();
    }

    @Override
    public Task findByName(String name) {
        Task task = taskRepository.findByName(name);
        if (task == null) {
            throw new EntityNotFoundException("Task: " + name + " not found");
        }
        return task;
        }

    @Override
    public List<Task> findAllByAssignedTo(Employee employee) {
        List<Task> tasks = taskRepository.findAllByAssignedTo(employee);
        if (tasks.isEmpty()){
            throw new EntityNotFoundException("Tasks to employee: " .concat(employee.getName().concat(employee.getSurname())).concat("with Title:")
                    .concat(employee.getJobTitle().getTitleName()).concat(" not found"));
        }

        return tasks;
    }

    @Override
    public List<Task> findTaskByImportance(TaskImportance importance) {
        List<Task> tasks = taskRepository.findTaskByImportance(importance);
        if (tasks.isEmpty()){
            throw new EntityNotFoundException("Tasks to with: " .concat(importance.getImportance().toString().toUpperCase().concat(" not found")));
        }

        return tasks;
    }


}
