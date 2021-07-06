package com.employeetracker.EmployeeTrackerAPI.service.impl;

import com.employeetracker.EmployeeTrackerAPI.dao.LocationRepository;
import com.employeetracker.EmployeeTrackerAPI.exceptions.EmailNotFoundException;
import com.employeetracker.EmployeeTrackerAPI.exceptions.UserNotFoundException;
import com.employeetracker.EmployeeTrackerAPI.exceptions.UsersNotAvailableException;
import com.employeetracker.EmployeeTrackerAPI.models.JobTitle;
import com.employeetracker.EmployeeTrackerAPI.models.Location;
import com.employeetracker.EmployeeTrackerAPI.models.User;
import com.employeetracker.EmployeeTrackerAPI.service.iface.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;

    @Autowired
    public LocationServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }



    @Override
    public String add(Location location) {
        locationRepository.save(location);
        return "Location has been successfully added.";
    }

    @Override
    public String update(Location location) {
        Optional<Location> locationFromDatabase = locationRepository.findById(location.getId());
        if (!locationFromDatabase.isPresent()) throw new EntityNotFoundException("Location with id " + location.getId() + " does not exist");
        // Carry date created timestamp
        location.setDate(locationFromDatabase.get().getDate());
        locationRepository.save(location);
        return "Location with ID " + location.getId() + " has been successfully updated.";
    }

    @Override
    public List<Location> getAll() {
        List<Location> locations = locationRepository.findAll();
        if (locations.isEmpty()) {
            throw new EntityNotFoundException("Locations not found");
        }
        return locations;
    }

    @Override
    public Location getOne(Long id) {
        Optional<Location> location = locationRepository.findById(id);
        if (!location.isPresent()){
            throw new EntityNotFoundException("Location with the ID " + id + " does not exist");
        }
        return location.get();
    }
    @Override
    public List<Location> findByLatitude(String latitude) {
        List<Location> location = locationRepository.findByLatitude(latitude);
        if (location == null){
            throw new EntityNotFoundException("Latitude " + latitude + " not found");
        }
        return location;
    }

    @Override
    public List<Location> findByLongitude(String longitude) {
        List<Location> location = locationRepository.findByLongitude(longitude);
        if (location == null){
            throw new EntityNotFoundException("Longitude " + longitude + " not found");
        }
        return location;
    }
}
