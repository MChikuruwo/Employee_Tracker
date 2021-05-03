package com.employeetracker.EmployeeTrackerAPI.service.iface;


import com.employeetracker.EmployeeTrackerAPI.models.BusinessUnit;

import java.util.List;

public interface BusinessUnitService {
    String add(BusinessUnit businessUnit);
    String update(BusinessUnit businessUnit);
    String delete(Integer id);
    List<BusinessUnit> getAll();
    BusinessUnit getOne(Integer id);
    BusinessUnit findByBusinessUnit(String businessUnit);
}
