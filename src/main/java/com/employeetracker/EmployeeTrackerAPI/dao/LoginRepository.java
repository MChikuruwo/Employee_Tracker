package com.employeetracker.EmployeeTrackerAPI.dao;

import com.employeetracker.EmployeeTrackerAPI.models.Login;
import com.employeetracker.EmployeeTrackerAPI.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Date;
import java.util.List;

@Repository
public interface LoginRepository extends JpaRepository<Login, Integer> {
    List<Login> findAllByUser(User user);
    List<Login> findAllByDate(Date date);
}
