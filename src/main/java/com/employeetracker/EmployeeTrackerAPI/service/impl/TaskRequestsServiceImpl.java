package com.employeetracker.EmployeeTrackerAPI.service.impl;

import com.employeetracker.EmployeeTrackerAPI.dao.TaskRequestRepository;
import com.employeetracker.EmployeeTrackerAPI.enums.TaskRequestAction;
import com.employeetracker.EmployeeTrackerAPI.enums.TaskRequestStatus;
import com.employeetracker.EmployeeTrackerAPI.exceptions.UnknownDutyException;
import com.employeetracker.EmployeeTrackerAPI.models.*;
import com.employeetracker.EmployeeTrackerAPI.service.iface.DelegationOfDutyService;
import com.employeetracker.EmployeeTrackerAPI.service.iface.EmployeeService;
import com.employeetracker.EmployeeTrackerAPI.service.iface.TaskRequestsService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TaskRequestsServiceImpl implements TaskRequestsService {

    private final TaskRequestRepository taskRequestRepository;
    private final DelegationOfDutyService delegationOfDutyService;
    private final EmployeeService employeeService;

    public TaskRequestsServiceImpl(TaskRequestRepository taskRequestRepository, DelegationOfDutyService delegationOfDutyService, EmployeeService employeeService) {
        this.taskRequestRepository = taskRequestRepository;
        this.delegationOfDutyService = delegationOfDutyService;
        this.employeeService = employeeService;
    }

    @Override
    public String add(TaskRequests taskRequests) {
        DelegationOfDuty duty = (DelegationOfDuty) taskRequests.getDuty();
        Employee subordinate = (Employee) taskRequests.getSubordinate();

        if (duty == null) {
            taskRequests.setTaskRequestStatus(TaskRequestStatus.REJECTED);
            taskRequests.setTaskRequestAction(TaskRequestAction.ADD);
            throw new UnknownDutyException("Duty requested does not exist");
        }
        if (duty.getEmployeeByAssignTo() == null) {
            throw new EntityNotFoundException("Employee does not exist on duty");
        } else {
            taskRequests.setTaskRequestAction(TaskRequestAction.ADD);
            taskRequests.setTaskRequestStatus(TaskRequestStatus.PENDING);
            taskRequestRepository.save(taskRequests);
            return "Task Request has been submitted successfully";
        }
    }

    @Override
    public List<TaskRequests> getAll() {
        List<TaskRequests> tasks = taskRequestRepository.findAll();
        if (tasks == null) {
            throw new EntityNotFoundException("Task Requests Not Found!");

        } else

            return tasks;
    }

    @Override
    public String update(TaskRequests taskRequests) {
        Optional<TaskRequests> detailsFromDatabase = taskRequestRepository.findById(taskRequests.getId());
        if (!detailsFromDatabase.isPresent()) throw new EntityNotFoundException("Task request does not exist");
        // Carry date created timestamp
        taskRequests.setDateUpdated(detailsFromDatabase.get().getDateUpdated());
        DelegationOfDuty findDuty = (DelegationOfDuty) taskRequests.getDuty();
        if (findDuty == null) {
            throw new UnknownDutyException("No such duty found.");
        } else {
            taskRequestRepository.save(taskRequests);
            return "Task request with ID " + taskRequests.getId() + " has been updated";
        }

    }

    @Override
    public String delete(Long id) {
        Optional<TaskRequests> detailsToDelete = taskRequestRepository.findById(id);
        if (!detailsToDelete.isPresent()) {
            throw new EntityNotFoundException("Task request with ID " + id + " does not exist");
        }
        taskRequestRepository.deleteById(id);
        return "Task has been successfully deleted.";
    }

    @Override
    public TaskRequests getOne(Long id) {
        Optional<TaskRequests> task = taskRequestRepository.findById(id);
        if (!task.isPresent()) {
            throw new EntityNotFoundException("Task request with the ID " + id + " does not exist");
        }
        return task.get();
    }

    @Override
    public TaskRequests findByName(String name) {
        TaskRequests taskRequests = taskRequestRepository.findByTaskName(name);
        if (taskRequests == null) {
            throw new EntityNotFoundException("Task request: " + name + " not found");
        }
        return taskRequests;
    }

    @Override
    public List<TaskRequests> findAllByDuty(DelegationOfDuty duty) {
        List<TaskRequests> tasks = taskRequestRepository.findAllByDuty(duty);
        if (tasks.isEmpty()) {
            throw new EntityNotFoundException("Task requests to duty: ".concat(duty.getDuty().concat("Assigned to:")
                    .concat(duty.getEmployeeByAssignTo().getName().concat(" ").concat(duty.getEmployeeByAssignTo().getSurname()).concat(" not found"))));
        }

        return tasks;
    }
}
