package com.thesis.orderservice.config;

import com.thesis.orderservice.dto.AppExceptionDTO;
import com.thesis.orderservice.dto.ErrorDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class RestHandlerException {

    @ExceptionHandler(value = {AppExceptionDTO.class})
    @ResponseBody
    public ResponseEntity<ErrorDTO> handleException(AppExceptionDTO ex){
        return ResponseEntity.status(ex.getCode()).body(ErrorDTO.builder().message(ex.getMessage()).build());
    }
}