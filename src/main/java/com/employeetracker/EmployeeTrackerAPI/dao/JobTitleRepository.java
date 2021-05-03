package com.employeetracker.EmployeeTrackerAPI.dao;

import com.employeetracker.EmployeeTrackerAPI.models.BusinessUnit;
import com.employeetracker.EmployeeTrackerAPI.models.Department;
import com.employeetracker.EmployeeTrackerAPI.models.JobTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface JobTitleRepository extends JpaRepository<JobTitle, Long> {
    List<JobTitle> findAllByBusinessUnit(BusinessUnit businessUnit);
    List<JobTitle> findAllByDepartment(Department department);
    List<JobTitle> findAllByBusinessUnitAndDepartment(BusinessUnit businessUnit, Department department);
}
