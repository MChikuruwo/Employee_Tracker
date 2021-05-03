package com.employeetracker.EmployeeTrackerAPI.service.iface;


import com.employeetracker.EmployeeTrackerAPI.models.Notices;

import java.util.List;

public interface NoticesService {
    String add(Notices notices);
    String update(Notices notices);
    String delete(Long id);
    List<Notices> getAll();
    Notices getOne(Long id);
    Notices findByTitle(String title);
}
