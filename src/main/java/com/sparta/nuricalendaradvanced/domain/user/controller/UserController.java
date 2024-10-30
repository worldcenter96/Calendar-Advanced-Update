package com.sparta.nuricalendaradvanced.domain.user.controller;

import com.sparta.nuricalendaradvanced.common.exception.ResponseException;
import com.sparta.nuricalendaradvanced.domain.user.dto.UserRequestDto;
import com.sparta.nuricalendaradvanced.domain.user.dto.UserResponseDto;
import com.sparta.nuricalendaradvanced.domain.user.entity.User;
import com.sparta.nuricalendaradvanced.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signUp(@RequestBody @Valid UserRequestDto requestDto) throws ResponseException {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.signUp(requestDto));

    }

    @PostMapping("/signin")
    public ResponseEntity<String> signIn(@RequestBody @Valid UserRequestDto requestDto, HttpServletResponse res) throws ResponseException, UnsupportedEncodingException {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.signIn(requestDto, res));


    }

    // 회원가입 시 이메일 중복 체크 용 API
    @GetMapping("/{email}")
    public ResponseEntity<Void> checkEmail(@PathVariable String email) throws ResponseException {

        userService.checkEmail(email);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();

    }

    @PutMapping()
    public ResponseEntity<UserResponseDto> updateUser(@RequestBody UserRequestDto requestDto, HttpServletRequest req) throws ResponseException {

        User user = (User) req.getAttribute("user");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.updateUser(requestDto, user));

    }

    @DeleteMapping()
    public ResponseEntity<Void> deleteUser(HttpServletRequest req) {

        User user = (User) req.getAttribute("user");
        userService.deleteUser(user);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();

    }


}
