package com.employeetracker.EmployeeTrackerAPI.service.impl;

import com.employeetracker.EmployeeTrackerAPI.dao.DelegationOfDutyRepository;
import com.employeetracker.EmployeeTrackerAPI.exceptions.UnknownManagerException;
import com.employeetracker.EmployeeTrackerAPI.models.DelegationOfDuty;
import com.employeetracker.EmployeeTrackerAPI.models.Employee;
import com.employeetracker.EmployeeTrackerAPI.service.iface.DelegationOfDutyService;
import com.employeetracker.EmployeeTrackerAPI.service.iface.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class DelegationOfDutyServiceImpl  implements DelegationOfDutyService {

    private final DelegationOfDutyRepository delegationOfDutyRepository;
    private final EmployeeService employeeService;

    @Autowired
    public DelegationOfDutyServiceImpl(DelegationOfDutyRepository delegationOfDutyRepository, EmployeeService employeeService) {
        this.delegationOfDutyRepository = delegationOfDutyRepository;
        this.employeeService = employeeService;
    }

    @Override
    public String add(DelegationOfDuty delegationOfDuty) {
        Employee findEmployee = employeeService.getOne(delegationOfDuty.getEmployeeByAssignedBy().getId());
        if(findEmployee==null){
            throw new UnknownManagerException("No such Manager Found!");
        }else

        delegationOfDutyRepository.save(delegationOfDuty);
        return "Delegation of duty request has been submitted";
    }

    @Override
    public String update(DelegationOfDuty delegationOfDuty) {
        Optional<DelegationOfDuty> detailsFromDatabase = delegationOfDutyRepository.findById(delegationOfDuty.getId());
        if (!detailsFromDatabase.isPresent()) throw new EntityNotFoundException("Delegation of duty  request does not exist");
        // Carry date created timestamp
        delegationOfDuty.setDateCreated(detailsFromDatabase.get().getDateCreated());
        Employee findEmployee = employeeService.findByUserId(delegationOfDuty.getEmployeeByAssignedBy().getId());
        if(findEmployee==null){
            throw new UnknownManagerException("No such Manager Found!");
        }else
        delegationOfDutyRepository.save(delegationOfDuty);
        return "Delegation of duty  request with ID " + delegationOfDuty.getId() + " has been updated";    }

    @Override
    public String delete(Long id) {
        Optional<DelegationOfDuty> detailsToDelete = delegationOfDutyRepository.findById(id);
        if (!detailsToDelete.isPresent()){
            throw new EntityNotFoundException("Delegation of duty  request with ID " + id + " does not exist");
        }
        delegationOfDutyRepository.deleteById(id);
        return "Delegation of duty  request has been deleted";    }

    @Override
    public List<DelegationOfDuty> getAll() {
        List<DelegationOfDuty> delegationOfDuties = delegationOfDutyRepository.findAll();
        if (delegationOfDuties.isEmpty()){
            throw new EntityNotFoundException("Delegation of duty  requests not found");
        }
        return delegationOfDuties;    }

    @Override
    public DelegationOfDuty getOne(Long id) {
        Optional<DelegationOfDuty> delegationOfDuty = delegationOfDutyRepository.findById(id);
        if (!delegationOfDuty.isPresent()){
            throw new EntityNotFoundException("Delegation of duty  request with the ID " + id + " does not exist");
        }
        return delegationOfDuty.get();    }

    @Override
    public List<DelegationOfDuty> findAllByAssignedEmployee(Employee employee) {
        List<DelegationOfDuty> delegationOfDuties = delegationOfDutyRepository.findAllByEmployeeByAssignTo(employee);
        if (delegationOfDuties.isEmpty()){
            throw new EntityNotFoundException("Delegation of  duty requests to employee: " .concat(employee.getName().concat(employee.getSurname())).concat("with Title:")
                    .concat(employee.getJobTitle().getTitleName()).concat(" not found"));
        }

        return delegationOfDuties;    }

    @Override
    public List<DelegationOfDuty> findAllByAssigningManager(Employee manager) {
        List<DelegationOfDuty> delegationOfDuties = delegationOfDutyRepository.findAllByEmployeeByAssignedBy(manager);
        if (delegationOfDuties.isEmpty()){
            throw new UnknownManagerException("Delegation of duty  requests from "
                    .concat(manager.getName()).concat(" ").concat(manager.getSurname()).concat(" not found"));
        }
        return delegationOfDuties;    }

    @Override
    public List<DelegationOfDuty> findAllByAssigningManagerAndAssignedEmployee(Employee manager, Employee employee) {
        List<DelegationOfDuty> delegationOfDuties = delegationOfDutyRepository.findAllByEmployeeByAssignedByAndAndEmployeeByAssignTo(manager, employee);
        if (delegationOfDuties.isEmpty()){
            throw new EntityNotFoundException("Delegation of duty request by manager:"
                    .concat(manager.getName().concat(manager.getSurname())).concat(" to employee:")
                    .concat(employee.getName().concat(employee.getSurname())).concat(" not found"));
        }
        return delegationOfDuties;    }

    @Override
    public List<DelegationOfDuty> findByIdAndEmployeeByAssignTo(Long id, Employee employee) {
        List<DelegationOfDuty> project = delegationOfDutyRepository.findByIdAndEmployeeByAssignTo(id,employee);
        if(project.isEmpty()){
            throw new EntityNotFoundException("Delegated task with id:"
            .concat(id.toString()).concat(" to employee:")
            .concat(employee.getName()).concat(employee.getSurname()).concat(" not found"));
        }
        return project;
    }
}
