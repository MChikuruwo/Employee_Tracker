package com.employeetracker.EmployeeTrackerAPI.dto;

import com.employeetracker.EmployeeTrackerAPI.models.User;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class AuditTrailDto implements Serializable {
    private Long id;
    private Timestamp dateAdded;
    private Timestamp dateUpdated;
    private User user;
}
