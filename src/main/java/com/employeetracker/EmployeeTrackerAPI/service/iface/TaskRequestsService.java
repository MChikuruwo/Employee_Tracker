package com.employeetracker.EmployeeTrackerAPI.service.iface;

import com.employeetracker.EmployeeTrackerAPI.models.*;

import java.util.List;

public interface TaskRequestsService {
    String add(TaskRequests taskRequests);
    List<TaskRequests> getAll();
    String update(TaskRequests taskRequests);
    String delete(Long id);
    TaskRequests getOne(Long id);

    TaskRequests findByName(String name);
    TaskRequests approveTaskRequest(Long taskId, Long dutyId);
    TaskRequests rejectTaskRequest(Long taskId,Long dutyId);
    List<TaskRequests> findAllByDuty(DelegationOfDuty duty);

    TaskRequests verifyTaskCompletion(Long taskId, Long dutyId);
    TaskRequests completeTask(Long taskId, Long dutyId);
    TaskRequests rejectTaskCompletion(Long taskId, Long dutyId);


}
