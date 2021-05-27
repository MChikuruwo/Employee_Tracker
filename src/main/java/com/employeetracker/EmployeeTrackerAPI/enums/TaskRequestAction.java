package com.employeetracker.EmployeeTrackerAPI.enums;

public enum TaskRequestAction {

    ADD("ADD"),
    COMPLETE("COMPLETE");

    private final String label;

    TaskRequestAction(String label) {
        this.label = label;
    }
}
