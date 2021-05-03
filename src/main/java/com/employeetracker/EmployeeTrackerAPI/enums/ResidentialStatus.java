package com.employeetracker.EmployeeTrackerAPI.enums;

public enum ResidentialStatus {
    PERMANENT("PERMANENT"),
    MORTGAGE("MORTGAGE"),
    RENTAL("RENTAL");

    public final String label;

    private ResidentialStatus(String label){
        this.label = label;
    }
}
