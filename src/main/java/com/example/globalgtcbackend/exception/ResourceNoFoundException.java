package com.example.globalgtcbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNoFoundException extends RuntimeException {

    public ResourceNoFoundException() {
        super();
    }

    public ResourceNoFoundException(String message) {
        super(message);
    }
    public ResourceNoFoundException(Throwable cause) {
        super(cause);
    }
    public ResourceNoFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
