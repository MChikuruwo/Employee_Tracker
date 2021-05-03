package com.employeetracker.EmployeeTrackerAPI.service.impl;

import com.employeetracker.EmployeeTrackerAPI.dao.DepartmentRepository;
import com.employeetracker.EmployeeTrackerAPI.models.Department;
import com.employeetracker.EmployeeTrackerAPI.service.iface.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }


    @Override
    public String add(Department department) {
        departmentRepository.save(department);
        return "Department has been successfully added";
    }

    @Override
    public String update(Department department) {
        Optional<Department> departmentFromDatabase = departmentRepository.findById(department.getId());
        if (!departmentFromDatabase.isPresent()) throw new EntityNotFoundException("Department does not exist!");
        departmentRepository.save(department);
        return "Department with ID " + department.getId() + " has been updated";
    }

    @Override
    public String delete(Integer id) {
        Optional<Department> departmentToDelete = departmentRepository.findById(id);
        if (!departmentToDelete.isPresent()){
            throw new EntityNotFoundException("Department with ID " + id + " does not exist");
        }
        departmentRepository.deleteById(id);
        return "Department has been deleted";
    }

    @Override
    public List<Department> getAll() {
        List<Department> departments = departmentRepository.findAll();
        if (departments.isEmpty()){
            throw new EntityNotFoundException("No Departments found");
        }
        return departments;
    }

    @Override
    public Department getOne(Integer id) {
        Optional<Department> department = departmentRepository.findById(id);
        if (!department.isPresent()){
            throw new EntityNotFoundException("Department with id " + id + " not found");
        }
        return department.get();
    }

    @Override
    public Department findByDepartment(String department) {
        Department department1 = departmentRepository.findByDepartment(department);
        if (department1 == null){
            throw new EntityNotFoundException("Department " + department + " not found");
        }
        return department1;
    }


}
