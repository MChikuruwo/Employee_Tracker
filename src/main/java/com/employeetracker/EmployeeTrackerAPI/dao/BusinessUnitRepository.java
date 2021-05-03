package com.employeetracker.EmployeeTrackerAPI.dao;

import com.employeetracker.EmployeeTrackerAPI.models.BusinessUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessUnitRepository extends JpaRepository<BusinessUnit, Integer> {
    BusinessUnit findByBusinessUnit(String businessUnit);
}
