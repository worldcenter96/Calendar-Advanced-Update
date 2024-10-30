package com.sparta.nuricalendaradvanced.domain.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j(topic = "exception:")
@RestControllerAdvice
public class GlobalExceptionHandler extends RuntimeException{

}
