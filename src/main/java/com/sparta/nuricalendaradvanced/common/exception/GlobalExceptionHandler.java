package com.sparta.nuricalendaradvanced.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j(topic = "exception:")
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = ResponseException.class)
    public ResponseEntity<String> responseException(ResponseException e) {
        return ResponseEntity
                .status(e.getResponseStatus().getHttpStatus())
                .body(e.getErrMessage());
    }


}
