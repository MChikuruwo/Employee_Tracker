package com.employeetracker.EmployeeTrackerAPI.api.config;

import java.io.Serializable;

public class ApiResponse implements Serializable {

    private Integer statusCode;
    private String message;
    private Object responseBody;

    public ApiResponse() {
    }

    public ApiResponse(Integer statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public ApiResponse(Integer statusCode, String message, Object responseBody) {
        this.statusCode = statusCode;
        this.message = message;
        this.responseBody = responseBody;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(Object responseBody) {
        this.responseBody = responseBody;
    }

    @Override
    public String toString() {
        return "{" +
                "statusCode='" + statusCode + '\'' +
                ", message='" + message + '\'' +
                ", responseBody=" + responseBody +
                '}';
    }
}
