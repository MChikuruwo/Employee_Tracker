package com.employeetracker.EmployeeTrackerAPI.service.impl;

import com.employeetracker.EmployeeTrackerAPI.dao.AnnualActivityCalendarRepository;
import com.employeetracker.EmployeeTrackerAPI.models.AnnualActivityCalendar;
import com.employeetracker.EmployeeTrackerAPI.service.iface.AnnualActivityCalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class AnnualActivityCalendarServiceImpl implements AnnualActivityCalendarService {

    private final AnnualActivityCalendarRepository annualActivityCalendarRepository;

    @Autowired
    public AnnualActivityCalendarServiceImpl(AnnualActivityCalendarRepository annualActivityCalendarRepository) {
        this.annualActivityCalendarRepository = annualActivityCalendarRepository;
    }

    @Override
    public String add(AnnualActivityCalendar annualActivityCalendar) {
        annualActivityCalendarRepository.save(annualActivityCalendar);
        return "Activity has been successfully added";
    }

    @Override
    public String update(AnnualActivityCalendar annualActivityCalendar) {
        Optional<AnnualActivityCalendar> activityFromDatabase = annualActivityCalendarRepository.findById(annualActivityCalendar.getId());
        if (!activityFromDatabase.isPresent()) throw new EntityNotFoundException("Activity not found!");
        annualActivityCalendarRepository.save(annualActivityCalendar);
        return "Activity with ID " + annualActivityCalendar.getId() + " has been successfully updated";
    }

    @Override
    public String delete(Long id) {
        Optional<AnnualActivityCalendar> activityToDelete = annualActivityCalendarRepository.findById(id);
        if (!activityToDelete.isPresent()){
            throw new EntityNotFoundException("Activity with ID " + id + " not found! ");
        }
        annualActivityCalendarRepository.deleteById(id);
        return "Activity has been successfully deleted";
    }

    @Override
    public List<AnnualActivityCalendar> getAll() {
        return annualActivityCalendarRepository.findAll();
    }

    @Override
    public AnnualActivityCalendar getOne(Long id) {
        Optional<AnnualActivityCalendar> annualActivityCalendar = annualActivityCalendarRepository.findById(id);
        if (!annualActivityCalendar.isPresent()){
            throw new EntityNotFoundException("Activity not found!");
        }
        return annualActivityCalendar.get();
    }

    @Override
    public AnnualActivityCalendar findByActivity(String activity) {
        AnnualActivityCalendar annualActivityCalendar = annualActivityCalendarRepository.findByActivity(activity);
        if (annualActivityCalendar == null){
            throw new EntityNotFoundException("Activity " + activity + " not found");
        }
        return annualActivityCalendar;
    }


}

