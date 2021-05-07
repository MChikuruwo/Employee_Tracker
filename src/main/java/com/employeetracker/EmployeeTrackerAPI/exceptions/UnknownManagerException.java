package com.employeetracker.EmployeeTrackerAPI.exceptions;

public class UnknownManagerException extends RuntimeException{

    public UnknownManagerException() {
        super();
    }

    public UnknownManagerException(String message) {
        super(message);
    }

    public UnknownManagerException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownManagerException(Throwable cause) {
        super(cause);
    }
}
