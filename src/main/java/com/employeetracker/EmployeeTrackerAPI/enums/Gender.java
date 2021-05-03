package com.employeetracker.EmployeeTrackerAPI.enums;

public enum Gender {
    M("M"),
    F("F");

    public final String label;

    private Gender(String label){
        this.label = label;
    }
}
