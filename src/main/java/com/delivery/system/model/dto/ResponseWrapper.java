package com.delivery.system.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseWrapper<T> {

    private final T data;
    private final Boolean success;
    private final String timestamp;
    private final String cause;
    private final String path;
    private List<String> details;
    private final int statusCode;


    /*public ResponseWrapper(Boolean success, T data, String cause, String path, int statusCode) {
        this.timestamp = Instant.now().toString();
        this.data = data;
        this.success = success;
        this.cause = cause;
        this.path = path;
        this.statusCode = statusCode;
    }

    public ResponseWrapper(Boolean success, T data, List<String> details, String cause, String path, int statusCode) {
        this.timestamp = Instant.now().toString();
        this.data = data;
        this.success = success;
        this.details = details;
        this.cause = cause;
        this.path = path;
        this.statusCode = statusCode;
    } */

    public ResponseWrapper(Boolean success, T data, int statusCode) {
        this.timestamp = Instant.now().toString();
        this.data = data;
        this.success = success;
        this.cause = null;
        this.path = null;
        this.statusCode = statusCode;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getCause() {
        return cause;
    }

    public String getPath() {
        return path;
    }

    public List<String> getDetails() {
        return details;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }

    public T getData() {
        return data;
    }

    public int getStatusCode() {
        return statusCode;
    }
}