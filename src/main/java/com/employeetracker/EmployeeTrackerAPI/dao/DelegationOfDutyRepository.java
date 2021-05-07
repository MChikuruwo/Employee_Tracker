package com.employeetracker.EmployeeTrackerAPI.dao;

import com.employeetracker.EmployeeTrackerAPI.models.DelegationOfDuty;
import com.employeetracker.EmployeeTrackerAPI.models.Employee;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
;

import java.util.List;

@Repository
public interface DelegationOfDutyRepository extends JpaRepository<DelegationOfDuty, Long> {
    List<DelegationOfDuty> findAllByEmployeeByAssignTo(Employee employee);
    List<DelegationOfDuty> findAllByEmployeeByAssignedBy(Employee employee);

    List<DelegationOfDuty> findAllByEmployeeByAssignedByAndAndEmployeeByAssignTo(Employee manager, Employee employee);

    List<DelegationOfDuty> findByIdAndEmployeeByAssignTo(Long id , Employee employee);


}
