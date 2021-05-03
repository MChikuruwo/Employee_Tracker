package com.employeetracker.EmployeeTrackerAPI.service.impl;

import com.employeetracker.EmployeeTrackerAPI.dao.EmployeeStatusRepository;
import com.employeetracker.EmployeeTrackerAPI.models.EmployeeStatus;
import com.employeetracker.EmployeeTrackerAPI.service.iface.EmployeeStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EmployeeStatusServiceImpl implements EmployeeStatusService {

    private final EmployeeStatusRepository employeeStatusRepository;

    @Autowired
    public EmployeeStatusServiceImpl(EmployeeStatusRepository employeeStatusRepository) {
        this.employeeStatusRepository = employeeStatusRepository;
    }

    @Override
    public String add(EmployeeStatus employeeStatus) {
        employeeStatusRepository.save(employeeStatus);
        return "Employee status has been added";
    }

    @Transactional
    @Override
    public String update(EmployeeStatus employeeStatus) {
        Optional<EmployeeStatus> statusFromDatabase = employeeStatusRepository.findById(employeeStatus.getId());
        if (!statusFromDatabase.isPresent()) throw new EntityNotFoundException("Employee status does not exist");
        employeeStatusRepository.save(employeeStatus);
        return "Employee status with ID " + employeeStatus.getId() + " has been updated";
    }

    @Transactional
    @Override
    public String delete(Long id) {
        Optional<EmployeeStatus> statusToDelete = employeeStatusRepository.findById(id);
        if (!statusToDelete.isPresent()){
            throw new EntityNotFoundException("Employee status with ID " + id + " does not exist");
        }
        employeeStatusRepository.deleteById(id);
        return "Employee status has been deleted";

    }

    @Override
    public List<EmployeeStatus> getAll() {
        List<EmployeeStatus> employeeStatuses = employeeStatusRepository.findAll();
        if (employeeStatuses.isEmpty()){
            throw new EntityNotFoundException("Employee statuses not found");
        }
        return employeeStatuses;
    }

    @Override
    public EmployeeStatus getOne(Long id) {
        Optional<EmployeeStatus> employeeStatus = employeeStatusRepository.findById(id);
        if (!employeeStatus.isPresent()){
            throw new EntityNotFoundException("Employee status with the ID " + id + " does not exist");
        }
        return employeeStatus.get();
    }

    @Override
    public EmployeeStatus findByStatus(String status) {
        EmployeeStatus employeeStatus = employeeStatusRepository.findByStatus(status);
        if (employeeStatus == null){
            throw new EntityNotFoundException("Employee status ".concat(status).concat(" not found"));
        }
        return employeeStatus;
    }


}
