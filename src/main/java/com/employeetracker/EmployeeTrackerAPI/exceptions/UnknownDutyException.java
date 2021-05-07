package com.employeetracker.EmployeeTrackerAPI.exceptions;

public class UnknownDutyException extends RuntimeException{

    public UnknownDutyException() {
        super();
    }

    public UnknownDutyException(String message) {
        super(message);
    }

    public UnknownDutyException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownDutyException(Throwable cause) {
        super(cause);
    }
}
