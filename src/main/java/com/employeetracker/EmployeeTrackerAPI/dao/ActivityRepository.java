package com.employeetracker.EmployeeTrackerAPI.dao;

import com.employeetracker.EmployeeTrackerAPI.models.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    @Query("SELECT a from Activity a WHERE a.entityId = ?1")
    List<Activity> getAllByEntityId(Long entityId);
}
