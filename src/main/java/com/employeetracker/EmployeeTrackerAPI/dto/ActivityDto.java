package com.employeetracker.EmployeeTrackerAPI.dto;

import com.employeetracker.EmployeeTrackerAPI.models.AuditTrail;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class ActivityDto implements Serializable {
    private Long id;
    private Long entityId;
    private String narrative;
    private Timestamp dateAdded;
    private AuditTrail auditTrail;
}
