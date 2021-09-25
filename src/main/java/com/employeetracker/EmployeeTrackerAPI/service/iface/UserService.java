package com.employeetracker.EmployeeTrackerAPI.service.iface;


import com.employeetracker.EmployeeTrackerAPI.models.User;

import java.util.List;

public interface UserService {
    String add(User user);
    String update(User user);
    String reset(User user);
    String delete(Integer id);
    List<User> getAll();
    User getOne(Integer id);
    User authUser(String emailAddress, String password) throws Exception;


    //User empCodeAuth(String employeeCode, String password) throws Exception;
    User findByEmailAddress(String emailAddress);
    User findByEmployeeCode(String employeeCode);
}
