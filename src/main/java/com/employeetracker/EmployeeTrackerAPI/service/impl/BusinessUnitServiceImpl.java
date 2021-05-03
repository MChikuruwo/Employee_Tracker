package com.employeetracker.EmployeeTrackerAPI.service.impl;

import com.employeetracker.EmployeeTrackerAPI.dao.BusinessUnitRepository;
import com.employeetracker.EmployeeTrackerAPI.models.BusinessUnit;
import com.employeetracker.EmployeeTrackerAPI.service.iface.BusinessUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BusinessUnitServiceImpl implements BusinessUnitService {

    private final BusinessUnitRepository businessUnitRepository;

    @Autowired
    public BusinessUnitServiceImpl(BusinessUnitRepository businessUnitRepository) {
        this.businessUnitRepository = businessUnitRepository;
    }


    @Override
    public String add(BusinessUnit businessUnit) {
        businessUnitRepository.save(businessUnit);
        return "Business Unit has been added";
    }

    @Override
    public String update(BusinessUnit businessUnit) {
        Optional<BusinessUnit> unitFromDatabase = businessUnitRepository.findById(businessUnit.getId());
        if (!unitFromDatabase.isPresent()) throw new EntityNotFoundException("Business Unit does not exist");
        businessUnitRepository.save(businessUnit);
        return "Business Unit with ID " + businessUnit.getId() + " has been updated";
    }

    @Override
    public String delete(Integer id) {
        Optional<BusinessUnit> businessUnitToDelete = businessUnitRepository.findById(id);
        if (!businessUnitToDelete.isPresent()){
            throw new EntityNotFoundException("Business unit with ID " + id + " does not exist");
        }
        businessUnitRepository.deleteById(id);
        return "Business Unit has been deleted";
    }

    @Override
    public List<BusinessUnit> getAll() {
        List<BusinessUnit> businessUnits = businessUnitRepository.findAll();
        if (businessUnits.isEmpty()){
            throw new EntityNotFoundException("No Business units found");
        }
        return businessUnits;
    }

    @Override
    public BusinessUnit getOne(Integer id) {
        Optional<BusinessUnit> businessUnit = businessUnitRepository.findById(id);
        if (!businessUnit.isPresent()){
            throw new EntityNotFoundException("Business Unit with id " + id + " not found");
        }
        return businessUnit.get();
    }

    @Override
    public BusinessUnit findByBusinessUnit(String businessUnit) {
        BusinessUnit businessUnit1 = businessUnitRepository.findByBusinessUnit(businessUnit);
        if (businessUnit1 == null){
            throw new EntityNotFoundException("Business Unit " + businessUnit + " not found");
        }
        return businessUnit1;
    }
}
