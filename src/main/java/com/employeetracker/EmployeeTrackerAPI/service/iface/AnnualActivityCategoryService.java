package com.employeetracker.EmployeeTrackerAPI.service.iface;


import com.employeetracker.EmployeeTrackerAPI.models.AnnualActivityCategory;

import java.util.List;

public interface AnnualActivityCategoryService {
    List<AnnualActivityCategory> getAll();
    AnnualActivityCategory getOne(Long id);
    AnnualActivityCategory findByCategory(String category);
}
