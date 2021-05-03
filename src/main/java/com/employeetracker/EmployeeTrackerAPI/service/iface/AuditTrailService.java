package com.employeetracker.EmployeeTrackerAPI.service.iface;


import com.employeetracker.EmployeeTrackerAPI.models.AuditTrail;

import java.util.List;

public interface AuditTrailService {
    String add(AuditTrail auditTrail);

    List<AuditTrail> getAll();

    List<AuditTrail> getByUserId(Long userId);

    AuditTrail getById(Long id);
}
