package com.sparta.nuricalendaradvanced.controller;

import com.sparta.nuricalendaradvanced.dto.UserRequestDto;
import com.sparta.nuricalendaradvanced.dto.UserResponseDto;
import com.sparta.nuricalendaradvanced.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public UserResponseDto signUp(@RequestBody @Valid UserRequestDto requestDto) {

        return userService.signUp(requestDto);

    }

    @GetMapping("/signin")
    public String signIn(@RequestBody @Valid UserRequestDto requestDto, HttpServletResponse res) {

        return userService.signIn(requestDto, res);

    }

    @GetMapping("/{email}")
    public String duplicateCheck(@PathVariable String email) {

        return userService.duplicateCheck(email);

    }

    @PutMapping("/modify")
    public UserResponseDto modifyUser(@RequestBody UserRequestDto requestDto, HttpServletRequest req) {

        return userService.modifyUser(requestDto, req);

    }

    @DeleteMapping("/delete")
    public String deleteUser(HttpServletRequest req) {

        return userService.deleteUser(req);

    }


}
