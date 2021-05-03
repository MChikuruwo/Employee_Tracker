package com.employeetracker.EmployeeTrackerAPI.service.impl;

import com.employeetracker.EmployeeTrackerAPI.dao.ActivityRepository;
import com.employeetracker.EmployeeTrackerAPI.exceptions.ActivityNotFoundException;
import com.employeetracker.EmployeeTrackerAPI.models.Activity;
import com.employeetracker.EmployeeTrackerAPI.service.iface.ActivityService;
import com.employeetracker.EmployeeTrackerAPI.utils.RefGen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;

    @Autowired
    public ActivityServiceImpl(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Override
    public String add(Activity activity) {
        String orderNumber = RefGen.next();
        activityRepository.save(activity);
        return "Activity added successfully";
    }

    @Override
    public Activity getById(Long id) {
        Optional<Activity> fromDateActivity = activityRepository.findById(id);

        if (!fromDateActivity.isPresent())
            throw new ActivityNotFoundException("Activity with ID " + id + " not found!");
        return fromDateActivity.get();
    }

    @Override
    public List<Activity> getAll() {
        return activityRepository.findAll();
    }

    @Override
    public List<Activity> getAllByEntityId(Long entityId) {
        return activityRepository.getAllByEntityId(entityId);
    }


}
