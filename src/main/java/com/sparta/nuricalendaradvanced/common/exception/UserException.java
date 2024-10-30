package com.sparta.nuricalendaradvanced.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class UserException extends RuntimeException {

    private final UserResponseStatus responseStatus;
    private final String errMessage;

    public UserException(UserResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
        this.errMessage = responseStatus.getErrMessage();
    }
}
