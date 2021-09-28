package com.employeetracker.EmployeeTrackerAPI.service.impl;

import com.employeetracker.EmployeeTrackerAPI.dao.TaskRequestRepository;
import com.employeetracker.EmployeeTrackerAPI.enums.TaskProgress;
import com.employeetracker.EmployeeTrackerAPI.enums.TaskRequestAction;
import com.employeetracker.EmployeeTrackerAPI.enums.TaskRequestStatus;
import com.employeetracker.EmployeeTrackerAPI.enums.TaskStatus;
import com.employeetracker.EmployeeTrackerAPI.exceptions.BusinessValidationException;
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
        DelegationOfDuty duty = (DelegationOfDuty) taskRequests.getDelegatedDuty();
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
        DelegationOfDuty findDuty = (DelegationOfDuty) taskRequests.getDelegatedDuty();
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
    public TaskRequests approveTaskRequest(Long taskId, Long dutyId) {
        TaskRequests taskRequests = taskRequestRepository.getOne(taskId);
          delegationOfDutyService.getOne(dutyId);
        if (taskRequests.getTaskRequestStatus() == TaskRequestStatus.PENDING) {
            taskRequests.setTaskRequestStatus(TaskRequestStatus.APPROVED);
            return taskRequestRepository.save(taskRequests);
        } else {
            //if task status is rejected, it cannot be approved
            throw new BusinessValidationException("Task request with id: " + taskId + " state is " + taskRequests.getTaskRequestStatus() + "  and cannot be approved.");
        }
    }

    @Override
    public TaskRequests rejectTaskRequest(Long taskId, Long dutyId) {
        TaskRequests taskRequest = taskRequestRepository.getOne(taskId);
        delegationOfDutyService.getOne(dutyId);
        if (taskRequest.getTaskRequestStatus() == TaskRequestStatus.PENDING) {
            taskRequest.setTaskRequestStatus(TaskRequestStatus.REJECTED);
            return taskRequestRepository.save(taskRequest);
        } else {
            //if task status is approved, it cannot be rejected
            throw new BusinessValidationException("Task request with id: " + taskId + " state is " + taskRequest.getTaskRequestStatus() + "  and cannot be rejected.");
        }
    }

    @Override
    public List<TaskRequests> findAllByDuty(DelegationOfDuty duty) {
        List<TaskRequests> tasks = taskRequestRepository.findAllByDelegatedDuty(duty);
        if (tasks.isEmpty()) {
            throw new EntityNotFoundException("Task requests to duty: ".concat(duty.getDuty().concat("Assigned to:")
                    .concat(duty.getEmployeeByAssignTo().getName().concat(" ").concat(duty.getEmployeeByAssignTo().getSurname()).concat(" not found"))));
        }

        return tasks;
    }

    @Override
    public TaskRequests verifyTaskCompletion(Long taskId, Long dutyId) {
        TaskRequests taskRequest = taskRequestRepository.getOne(taskId);
        delegationOfDutyService.getOne(dutyId);
        if (taskRequest.getTaskStatus() == TaskStatus.ACTIVE){
            taskRequest.setTaskProgress(TaskProgress.DONE);
            taskRequest.setTaskStatus(TaskStatus.COMPLETE);
            return taskRequestRepository.save(taskRequest);
        } else {
            //if task status is complete, it cannot be pending
            throw new BusinessValidationException("Task request with id: " + taskId + " state is " + taskRequest.getTaskStatus() + "  and cannot be pending.");
        }
    }

    @Override
    public TaskRequests completeTask(Long taskId, Long dutyId) {
        TaskRequests taskRequest = taskRequestRepository.getOne(taskId);
        delegationOfDutyService.getOne(dutyId);
        if (taskRequest.getTaskProgress() == TaskProgress.PARTIAL){
            taskRequest.setTaskProgress(TaskProgress.DONE);
            return taskRequestRepository.save(taskRequest);
        } else {
            //if task status is complete, it cannot be pending
            throw new BusinessValidationException("Task request with id: " + taskId + " progress is " + taskRequest.getTaskProgress() + "  and cannot be initial.");
        }
    }

    @Override
    public TaskRequests rejectTaskCompletion(Long taskId, Long dutyId) {
        TaskRequests taskRequest = taskRequestRepository.getOne(taskId);
        delegationOfDutyService.getOne(dutyId);
        if (taskRequest.getTaskProgress() == TaskProgress.DONE){
            taskRequest.setTaskProgress(TaskProgress.PARTIAL);
            return taskRequestRepository.save(taskRequest);
        } else {
            //if task status is complete, it cannot be pending
            throw new BusinessValidationException("Task request with id: " + taskId + " progress is " + taskRequest.getTaskProgress() + "  and cannot be done.");
        }
    }
}
