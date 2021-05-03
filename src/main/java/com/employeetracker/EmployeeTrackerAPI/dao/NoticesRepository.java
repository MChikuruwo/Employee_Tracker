package com.employeetracker.EmployeeTrackerAPI.dao;

import com.employeetracker.EmployeeTrackerAPI.models.Notices;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticesRepository extends JpaRepository<Notices, Long> {
    Notices findByTitle(String title);
}
