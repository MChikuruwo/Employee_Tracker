package com.employeetracker.EmployeeTrackerAPI.service.iface;



import com.employeetracker.EmployeeTrackerAPI.models.AnnualActivityCalendar;

import java.util.List;

public interface AnnualActivityCalendarService {
    String add(AnnualActivityCalendar annualActivityCalendar);
    String update(AnnualActivityCalendar annualActivityCalendar);
    String delete(Long id);
    List<AnnualActivityCalendar> getAll();
    AnnualActivityCalendar getOne(Long id);
    AnnualActivityCalendar findByActivity(String activity);

}
