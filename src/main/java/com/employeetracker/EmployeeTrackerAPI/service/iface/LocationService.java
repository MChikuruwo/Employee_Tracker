package com.employeetracker.EmployeeTrackerAPI.service.iface;

import com.employeetracker.EmployeeTrackerAPI.models.Location;

import java.util.List;

public interface LocationService {
    String add(Location location);
    String update(Location location);
    List<Location> getAll();
    Location getOne(Long id);

    List<Location> findByLatitude(String latitude);
    List<Location> findByLongitude(String longitude);
}
