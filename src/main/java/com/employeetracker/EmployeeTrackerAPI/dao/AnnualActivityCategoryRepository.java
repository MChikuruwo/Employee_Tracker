package com.employeetracker.EmployeeTrackerAPI.dao;

import com.employeetracker.EmployeeTrackerAPI.models.AnnualActivityCategory;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AnnualActivityCategoryRepository extends JpaRepository<AnnualActivityCategory,Long> {
    AnnualActivityCategory findByCategory(String category);
}
