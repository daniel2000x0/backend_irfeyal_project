package com.irfeyal.asistencia.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ApiResponse<T> {

    private int status;
    private String message;
    private T data;
    private List<String> errors;
    private LocalDateTime timestamp;

    public ApiResponse() {
        this.timestamp = LocalDateTime.now();
    }

    public ApiResponse(int status, String message, T data) {
        this();
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(200, "OK", data);
    }

    public static <T> ApiResponse<T> created(T data) {
        return new ApiResponse<>(201, "Creado", data);
    }

    public static <T> ApiResponse<T> notFound(String message) {
        ApiResponse<T> res = new ApiResponse<>();
        res.status = 404;
        res.message = message;
        return res;
    }

    public static <T> ApiResponse<T> badRequest(List<String> errors) {
        ApiResponse<T> res = new ApiResponse<>();
        res.status = 400;
        res.message = "Error de validación";
        res.errors = errors;
        return res;
    }

    public static <T> ApiResponse<T> error(int status, String message, String detail) {
        ApiResponse<T> res = new ApiResponse<>();
        res.status = status;
        res.message = message;
        res.errors = List.of(detail);
        return res;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

}
