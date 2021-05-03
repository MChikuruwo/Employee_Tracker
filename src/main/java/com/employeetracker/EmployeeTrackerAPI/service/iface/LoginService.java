package com.employeetracker.EmployeeTrackerAPI.service.iface;




import com.employeetracker.EmployeeTrackerAPI.models.Login;
import com.employeetracker.EmployeeTrackerAPI.models.User;

import java.util.Date;
import java.util.List;

public interface LoginService {
    Login add(Login login);
    List<Login> getAll();
    Login getOne(Integer id);

    List<Login> findAllByUser(User user);
    List<Login> findAllByDate(Date date);
}
