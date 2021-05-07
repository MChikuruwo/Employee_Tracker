package com.employeetracker.EmployeeTrackerAPI.enums;

public enum TaskStatus {
    PENDING("PENDING"),
    ACTIVE("ACTIVE"),
    COMPLETE("COMPLETE");

    public final String label;

    private TaskStatus(String label){
        this.label = label;
    }

}
