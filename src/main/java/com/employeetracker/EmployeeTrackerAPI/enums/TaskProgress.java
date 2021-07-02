package com.employeetracker.EmployeeTrackerAPI.enums;

public enum TaskProgress {
    INITIAL("INITIAL"),
    PARTIAL("PARTIAL"),
    DONE("DONE");

    private final String label;

    TaskProgress(String label) {
        this.label = label;
    }
}
