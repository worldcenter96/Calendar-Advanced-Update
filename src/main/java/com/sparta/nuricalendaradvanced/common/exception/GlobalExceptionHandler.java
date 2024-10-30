package com.sparta.nuricalendaradvanced.common.exception;

import jakarta.servlet.ServletException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Slf4j(topic = "exception:")
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = ResponseException.class)
    public ResponseEntity<String> responseException(ResponseException e) {
        return ResponseEntity
                .status(e.getResponseStatus().getHttpStatus())
                .body(e.getErrMessage());
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> responseException(IOException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    @ExceptionHandler(ServletException.class)
    public ResponseEntity<String> responseException(ServletException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    @ExceptionHandler(UnsupportedEncodingException.class)
    public ResponseEntity<String> responseException(UnsupportedEncodingException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }


}
