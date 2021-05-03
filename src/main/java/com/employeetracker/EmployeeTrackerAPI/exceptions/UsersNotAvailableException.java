package com.employeetracker.EmployeeTrackerAPI.exceptions;

public class UsersNotAvailableException extends RuntimeException {

    public UsersNotAvailableException() {
        super();
    }

    public UsersNotAvailableException(String message) {
        super(message);
    }

    public UsersNotAvailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public UsersNotAvailableException(Throwable cause) {
        super(cause);
    }
}
