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
    List<TaskRequests> findAllByDuty(DelegationOfDuty duty);
}
