package com.example.my.common.exception.handler;


import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.my.common.dto.ResponseDTO;
import com.example.my.common.exception.BadRequestException;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(BindException.class)
        public ResponseEntity<?> handleBindException(BindException bindException){
            HashMap<String, String> errorMap = new HashMap<>();

            for (FieldError fieldError : bindException.getBindingResult().getFieldErrors()) {
                errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
                // System.out.println(fieldError.getField());
                // System.out.println(fieldError.getDefaultMessage());
            }

            return new ResponseEntity<>(
                ResponseDTO.builder()
                .code(1)
                .message("요청 데이터를 확인해주세요")
                .data(errorMap)
                .build(),
                HttpStatus.BAD_REQUEST
            );
        }

    // 에러별로 만들기 (추천)
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleBadRequestException(Exception exception){
        exception.printStackTrace();
        return new ResponseEntity<>(
        ResponseDTO.builder()
                .code(1)
                .message(exception.getMessage())
                .build(),
            HttpStatus.BAD_REQUEST);    
        
    }

    //모든 에러를 처리하고 싶다
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception exception){
        exception.printStackTrace();  // 함수들이 실행되는 것을 수정한다. (어디서 에러가 발생했는지 추적)
        return new ResponseEntity<>(
            ResponseDTO.builder()
                .code(1)
                .message(exception.getMessage())
                .build(),
            HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
}
