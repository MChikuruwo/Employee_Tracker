package com.employeetracker.EmployeeTrackerAPI.service.iface;


import com.employeetracker.EmployeeTrackerAPI.models.Activity;

import java.util.List;

public interface ActivityService {
    String add(Activity activity);

    Activity getById(Long id);

    List<Activity> getAll();

    List<Activity> getAllByEntityId(Long entityId);

}
