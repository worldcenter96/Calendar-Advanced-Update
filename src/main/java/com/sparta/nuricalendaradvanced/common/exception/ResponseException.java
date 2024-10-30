package com.sparta.nuricalendaradvanced.common.exception;

import lombok.Getter;

@Getter
public class ResponseException extends RuntimeException {

    private final ResponseStatus responseStatus;
    private final String errMessage;

    public ResponseException(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
        this.errMessage = responseStatus.getErrMessage();
    }

}
