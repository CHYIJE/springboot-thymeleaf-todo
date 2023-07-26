package com.example.my.common.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.my.common.dto.ResponseDTO;
import com.example.my.common.exception.BadRequestException;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleBadRequestException(Exception exception){
        return new ResponseEntity<>(
        ResponseDTO.builder()
                .code(1)
                .message(exception.getMessage())
                .build(),
            HttpStatus.BAD_REQUEST);    
        
    }
    
}
