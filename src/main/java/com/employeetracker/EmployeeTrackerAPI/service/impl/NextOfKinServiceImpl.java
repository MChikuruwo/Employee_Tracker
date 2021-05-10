package com.employeetracker.EmployeeTrackerAPI.service.impl;

import com.employeetracker.EmployeeTrackerAPI.dao.NextOfKinRepository;
import com.employeetracker.EmployeeTrackerAPI.models.Employee;
import com.employeetracker.EmployeeTrackerAPI.models.NextOfKin;
import com.employeetracker.EmployeeTrackerAPI.service.iface.NextOfKinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class NextOfKinServiceImpl implements NextOfKinService {

    private final NextOfKinRepository nextOfKinRepository;

    @Autowired
    public NextOfKinServiceImpl(NextOfKinRepository nextOfKinRepository) {
        this.nextOfKinRepository = nextOfKinRepository;
    }

    @Override
    public String add(NextOfKin nextOfKin) {
        nextOfKinRepository.save(nextOfKin);
        return "Employee Next of Kin has been successfully added.";
    }

    @Transactional
    @Override
    public String update(NextOfKin nextOfKin) {
        Optional<NextOfKin> fromDatabase = nextOfKinRepository.findById(nextOfKin.getId());
        if (!fromDatabase.isPresent()) throw new EntityNotFoundException("Next of kin does not exist");
        // Carry date created timestamp
        nextOfKin.setDateCreated(fromDatabase.get().getDateCreated());
        nextOfKinRepository.save(nextOfKin);
        return "Next of kin with ID " + nextOfKin.getId() + " has been updated";
    }

    @Transactional
    @Override
    public String delete(Long id) {
        Optional<NextOfKin> toDelete = nextOfKinRepository.findById(id);
        if (!toDelete.isPresent()){
            throw new EntityNotFoundException("Next of kin with ID " + id + " does not exist");
        }
        nextOfKinRepository.deleteById(id);
        return "Next of kin has been deleted";

    }

    @Override
    public List<NextOfKin> getAll() {
        List<NextOfKin> nextOfKins = nextOfKinRepository.findAll();
        if (nextOfKins.isEmpty()){
            throw new EntityNotFoundException("Next of kins not found");
        }
        return nextOfKins;
    }

    @Override
    public NextOfKin getOne(Long id) {
        Optional<NextOfKin> nextOfKin = nextOfKinRepository.findById(id);
        if (!nextOfKin.isPresent()){
            throw new EntityNotFoundException("Next of kin with the ID " + id + " does not exist");
        }
        return nextOfKin.get();
    }

    @Override
    public NextOfKin findByEmployee(Employee employee) {
        NextOfKin nextOfKin = nextOfKinRepository.findByEmployee(employee);
        if (nextOfKin == null){
            throw new EntityNotFoundException("Next of kin for employee "
                    .concat(employee.getEmployeeCode()).concat(" not found"));
        }
        return nextOfKin;
    }


}
