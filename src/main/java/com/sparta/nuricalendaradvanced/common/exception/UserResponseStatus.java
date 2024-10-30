package com.sparta.nuricalendaradvanced.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserResponseStatus {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 회원은 존재하지 않습니다."),
    USER_NAME_DUPLICATED(HttpStatus.BAD_REQUEST, "이미 가입한 유저 입니다."),
    USER_EMAIL_DUPLICATED(HttpStatus.BAD_REQUEST, "중복된 Email 입니다."),
    USER_PASSWORD_NOT_MATCH(HttpStatus.UNAUTHORIZED, "암호가 일치하지 않습니다."),
    ADMIN_PASSWORD_NOT_MATCH(HttpStatus.UNAUTHORIZED, "관리자 암호가 틀려 등록이 불가능합니다.");

    private final HttpStatus httpStatus;
    private final String errMessage;

}
