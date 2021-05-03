package com.employeetracker.EmployeeTrackerAPI.service.iface;



import com.employeetracker.EmployeeTrackerAPI.models.Role;

import java.util.List;

public interface RoleService {
    List<Role> getAll();
    Role getOne(Integer id);
    Role findByName(String name);
}
