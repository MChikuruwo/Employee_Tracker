package com.employeetracker.EmployeeTrackerAPI.service.iface;




import com.employeetracker.EmployeeTrackerAPI.models.BusinessUnit;
import com.employeetracker.EmployeeTrackerAPI.models.Department;
import com.employeetracker.EmployeeTrackerAPI.models.JobTitle;

import java.util.List;

public interface JobTitleService {
    String add(JobTitle jobTitle);
    String update(JobTitle jobTitle);
    String delete(Long id);
    List<JobTitle> getAll();
    JobTitle getOne(Long id);

    List<JobTitle> findAllByBusinessUnit(BusinessUnit businessUnit);
    List<JobTitle> findAllByDepartment(Department department);
    List<JobTitle> findAllByBusinessUnitAndDepartment(BusinessUnit businessUnit, Department department);
}
