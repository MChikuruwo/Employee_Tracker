package com.employeetracker.EmployeeTrackerAPI.enums;

public enum TaskRequestStatus {
    PENDING("PENDING"),
    APPROVED("APPROVED"),
    REJECTED("REJECTED");

    private final String label;

    TaskRequestStatus(String label) {
        this.label = label;
    }
}
