package com.employeetracker.EmployeeTrackerAPI.exceptions;

public class BusinessValidationException extends RuntimeException{

    public BusinessValidationException() {
        super();
    }

    public BusinessValidationException(String message) {
        super(message);
    }

    public BusinessValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessValidationException(Throwable cause) {
        super(cause);
    }
}
