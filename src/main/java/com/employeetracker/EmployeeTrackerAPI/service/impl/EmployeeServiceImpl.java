package com.employeetracker.EmployeeTrackerAPI.service.impl;

import com.employeetracker.EmployeeTrackerAPI.dao.EmployeeRepository;
import com.employeetracker.EmployeeTrackerAPI.dao.RoleRepository;
import com.employeetracker.EmployeeTrackerAPI.enums.Gender;
import com.employeetracker.EmployeeTrackerAPI.exceptions.EmployeeAlreadyExistsException;
import com.employeetracker.EmployeeTrackerAPI.models.*;
import com.employeetracker.EmployeeTrackerAPI.service.iface.EmployeeService;
import com.employeetracker.EmployeeTrackerAPI.service.iface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService, UserDetailsService {

    private final EmployeeRepository employeeRepository;
    private User user;
    private  final UserService userService;
    private  final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;



    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository,UserService userService, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String add(Employee employee) {
        Optional<Employee> employeeFromDatabase = Optional.ofNullable(employeeRepository.findEmployeeByEmployeeCode(employee.getEmployeeCode()));
       if (employeeFromDatabase.isPresent()) throw new EmployeeAlreadyExistsException("Employee Already exists!");
        employeeRepository.save(employee);

        return "Employee has been successfully added.";
    }

    @Transactional
    @Override
    public String update(Employee employee) {
        Optional<Employee> employeeFromDatabase = employeeRepository.findById(employee.getId());
        if (!employeeFromDatabase.isPresent()) throw new EntityNotFoundException("Employee does not exist");
        // Carry date created timestamp
        employee.setDateCreated(employeeFromDatabase.get().getDateCreated());
        employeeRepository.save(employee);
        return "Employee with ID " + employee.getId() + " has been updated";
    }

    @Transactional
    @Override
    public String delete(Integer id) {
        Optional<Employee> employeeToDelete = employeeRepository.findById(id);
        if (!employeeToDelete.isPresent()){
            throw new EntityNotFoundException("Employee with ID " + id + " does not exist");
        }
        employeeRepository.deleteById(id);
        return "Employee has been deleted";

    }

    @Override
    public List<Employee> getAll() {
        List<Employee> employees = employeeRepository.findAll();
        if (employees.isEmpty()){
            throw new EntityNotFoundException("Employees not found");
        }
        return employees;
    }

    @Override
    public Employee getOne(Integer id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (!employee.isPresent()){
            throw new EntityNotFoundException("Employee with the ID " + id + " does not exist");
        }
        return employee.get();
    }

    @Override
    public User empCodeAuth(String employeeCode, String password) throws Exception{
        //First get the user by employee code to check if the user exists
        User user = userService.findByEmployeeCode(employeeCode);
        if (user == null){
            //Display an error that the user with the employee code was not found
            throw new EntityNotFoundException("User with employeeCode: " + employeeCode + " not found");
        }
        //Check user entered password if it matches hashed password in database
        if (!passwordEncoder.matches(password, user.getPassword())){
            throw new BadCredentialsException("Incorrect password");
        }
        //Else return the user if found
        return user;
    }

    @Override
    public Employee findByUserId(Integer userId) {
        Employee employee = employeeRepository.findByUserId(userId);
        if (employee == null){
            throw new EntityNotFoundException("Employee with user id " + userId + " not found");
        }
        return employee;
    }

    @Override
    public Employee findByEmployeeCode(String employeeCode) {
        Employee employee = employeeRepository.findByEmployeeCode(employeeCode);
        if (employee == null){
            throw new EntityNotFoundException("Employee with code " + employeeCode + " not found");
        }
        return employee;
    }

    @Override
    public List<Employee> findAllByEmployeeStatus(EmployeeStatus employeeStatus) {
        List<Employee> employees = employeeRepository.findAllByEmployeeStatus(employeeStatus);
        if (employees.isEmpty()){
            throw new EntityNotFoundException("Employees with employee status ".concat(employeeStatus.getStatus()).concat(" not found"));
        }
        return employees;
    }

    @Override
    public List<Employee> findAllByJobTitle(JobTitle jobTitle) {
        List<Employee> employees = employeeRepository.findAllByJobTitle(jobTitle);
        if (employees.isEmpty()){
            throw new EntityNotFoundException("Employees with job title ".concat(jobTitle.getTitleName()).concat(" not found"));
        }
        return employees;
    }

    @Override
    public List<Employee> findAllByResidentialStatus(String residentialStatus) {
        List<Employee> employees = employeeRepository.findAllByResidentialStatus(residentialStatus);
        if (employees.isEmpty()){
            throw new EntityNotFoundException("Employees with residential status ".concat(residentialStatus).concat(" not found"));
        }
        return employees;
    }

    @Override
    public List<Employee> findAllByGender(String gender) {
        List<Employee> employees = employeeRepository.findAllByGender(gender);
        if (employees.isEmpty()){
            throw new EntityNotFoundException("Employees with gender ".concat(gender).concat(" not found"));
        }
        return employees;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String employeeCode) throws UsernameNotFoundException {
        User user = userService.findByEmployeeCode(employeeCode);
        roleRepository.findByName(user.getRole().toString());
        if (user == null){
            //TODO: Set an error that the user by that email address cannot be found
            throw new UsernameNotFoundException("Could not find the user " + employeeCode);

        }
        return new UserPrincipal(user);
    }


}
