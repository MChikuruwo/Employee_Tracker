package com.employeetracker.EmployeeTrackerAPI.dao;

import com.employeetracker.EmployeeTrackerAPI.models.AnnualActivityCalendar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnualActivityCalendarRepository extends JpaRepository<AnnualActivityCalendar,Long> {
    AnnualActivityCalendar findByActivity(String activity);
}
