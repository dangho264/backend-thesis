package com.thesis.orderservice.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AppExceptionDTO extends RuntimeException {
    private final HttpStatus code;

    public AppExceptionDTO(String message, HttpStatus code) {
        super(message);
        this.code = code;
    }
}
