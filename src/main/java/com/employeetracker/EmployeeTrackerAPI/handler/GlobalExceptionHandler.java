package com.employeetracker.EmployeeTrackerAPI.handler;

import com.employeetracker.EmployeeTrackerAPI.api.config.ApiResponse;
import com.employeetracker.EmployeeTrackerAPI.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import javax.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity handleUserException (UserNotFoundException e) {
        ApiResponse response = new ApiResponse(404,  e.getMessage());
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ApiResponse handleEntityNotFoundException (EntityNotFoundException e) {
        return new ApiResponse(404, e.getLocalizedMessage());

    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidTokenException.class)
    public ApiResponse handleInvalidTokenException (InvalidTokenException e) {
        return new ApiResponse(400, e.getLocalizedMessage());

    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidOldPasswordException.class)
    public ApiResponse handleInvalidOldPasswordException (InvalidOldPasswordException e) {
        return new ApiResponse(400, e.getLocalizedMessage());

    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EmailNotFoundException.class)
    public ApiResponse handleEmailNotFoundException (EmailNotFoundException e) {
        return new ApiResponse(400, e.getLocalizedMessage());

    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EmployeeCodeNotFoundException.class)
    public ApiResponse handleEmployeeCodeNotFoundException (EmployeeCodeNotFoundException e) {
        return new ApiResponse(400, e.getLocalizedMessage());

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UsersNotAvailableException.class)
    public ApiResponse handleUsersNotAvailableException (UsersNotAvailableException e) {
        return new ApiResponse(400, e.getLocalizedMessage());

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EmployeeAlreadyExistsException.class)
    public ApiResponse handleEmployeeAlreadyExistsException (EmployeeAlreadyExistsException e) {
        return new ApiResponse(400, e.getLocalizedMessage());

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ApiResponse handleUserAlreadyExistsException (UserAlreadyExistsException e) {
        return new ApiResponse(400, e.getLocalizedMessage());

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ActivityNotFoundException.class)
    public ApiResponse handleActivityNotFoundException (ActivityNotFoundException e) {
        return new ApiResponse(400, e.getLocalizedMessage());

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UnknownManagerException.class)
    public ApiResponse handleUnknownManagerException (UnknownManagerException e) {
        return new ApiResponse(400, e.getLocalizedMessage());

    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UnknownDutyException.class)
    public ApiResponse handleUnknownDutyException (UnknownDutyException e) {
        return new ApiResponse(400, e.getLocalizedMessage());

    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public ApiResponse handleGeneralException(Exception e) {
        return new ApiResponse(400, e.getLocalizedMessage());
    }
}
